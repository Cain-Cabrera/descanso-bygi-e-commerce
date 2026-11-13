package com.descansobygi.integration;

import com.descansobygi.dto.itemCarrito.ItemCarritoRequestDTO;
import com.descansobygi.dto.pedido.PedidoRequestDTO;
import com.descansobygi.enums.EstadoCarrito;
import com.descansobygi.enums.FormaDePago;
import com.descansobygi.enums.Rol;
import com.descansobygi.exception.BadRequestException;
import com.descansobygi.model.Carrito;
import com.descansobygi.model.Categoria;
import com.descansobygi.model.Marca;
import com.descansobygi.model.Producto;
import com.descansobygi.model.Usuario;
import com.descansobygi.repository.carrito.CarritoRepository;
import com.descansobygi.repository.carrito.ItemCarritoRepository;
import com.descansobygi.repository.catalogo.CategoriaRepository;
import com.descansobygi.repository.catalogo.MarcaRepository;
import com.descansobygi.repository.catalogo.ProductoRepository;
import com.descansobygi.repository.pedidos.PedidoRepository;
import com.descansobygi.repository.usuarios.UsuarioRepository;
import com.descansobygi.service.carrito.ICarritoService;
import com.descansobygi.service.pedidos.IPedidoService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
class PersistenciaPedidoTest {

    @Autowired
    private IPedidoService pedidoService;

    @Autowired
    private ICarritoService carritoService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CarritoRepository carritoRepository;

    @Autowired
    private ItemCarritoRepository itemCarritoRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private MarcaRepository marcaRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    void confirmarPedidoGuardaPreciosDescuentaStockYCancelarLoReintegra() {
        Usuario usuario = crearUsuario();
        Producto producto = crearProducto(5);
        Carrito carrito = new Carrito();
        carrito.setEstado(EstadoCarrito.ACTIVO);
        carrito.setUsuario(usuario);
        carrito = carritoRepository.save(carrito);

        carritoService.agregarAlCarrito(usuario.getId(),
                new ItemCarritoRequestDTO(2, producto.getId()));
        // Simula requests HTTP separados: deja que la siguiente transacción recargue el carrito y sus items.
        entityManager.flush();
        entityManager.clear();
        var pedidoCreado = pedidoService.crearPedido(usuario.getId(),
                new PedidoRequestDTO(FormaDePago.TRANSFERENCIA));

        assertEquals(0, new BigDecimal("21.00").compareTo(pedidoCreado.total()));
        assertEquals(FormaDePago.TRANSFERENCIA, pedidoCreado.formaDePago());
        assertEquals(1, pedidoCreado.detalles().size());
        assertEquals(0, new BigDecimal("10.50").compareTo(pedidoCreado.detalles().getFirst().precioUnitario()));
        assertEquals(0, new BigDecimal("21.00").compareTo(pedidoCreado.detalles().getFirst().subtotal()));

        entityManager.flush();
        entityManager.clear();

        assertEquals(3, productoRepository.findById(producto.getId()).orElseThrow().getStock());
        assertTrue(itemCarritoRepository.findByCarritoIdAndEliminadoFalse(carrito.getId()).isEmpty());
        assertFalse(pedidoRepository.findById(pedidoCreado.id()).orElseThrow().isEliminado());

        var pedidoCancelado = pedidoService.cancelarPedido(pedidoCreado.id());
        entityManager.flush();
        entityManager.clear();

        assertTrue(pedidoCancelado.cancelado());
        assertEquals(5, productoRepository.findById(producto.getId()).orElseThrow().getStock());
        assertTrue(pedidoRepository.findById(pedidoCreado.id()).orElseThrow().isEliminado());
    }

    @Test
    void noConfirmaPedidoSiElStockCambioDesdeQueSeAgregoElItem() {
        Usuario usuario = crearUsuario();
        Producto producto = crearProducto(1);
        Carrito carrito = new Carrito();
        carrito.setEstado(EstadoCarrito.ACTIVO);
        carrito.setUsuario(usuario);
        carrito = carritoRepository.save(carrito);
        carritoService.agregarAlCarrito(usuario.getId(),
                new ItemCarritoRequestDTO(1, producto.getId()));

        producto.setStock(0);
        productoRepository.save(producto);
        entityManager.flush();
        entityManager.clear();

        assertThrows(BadRequestException.class, () -> pedidoService.crearPedido(
                usuario.getId(), new PedidoRequestDTO(FormaDePago.EFECTIVO)));
    }

    private Usuario crearUsuario() {
        Usuario usuario = new Usuario();
        usuario.setNombre("Usuario de prueba");
        usuario.setApellido("Integración");
        usuario.setMail("pedido-" + UUID.randomUUID() + "@ejemplo.com");
        usuario.setContrasena("no-utilizable-en-login");
        usuario.setRol(Rol.USUARIO);
        return usuarioRepository.save(usuario);
    }

    private Producto crearProducto(int stock) {
        String identificador = UUID.randomUUID().toString();
        Categoria categoria = new Categoria();
        categoria.setNombre("Categoría de pedido " + identificador);
        categoria = categoriaRepository.save(categoria);

        Marca marca = new Marca();
        marca.setNombre("Marca de pedido " + identificador);
        marca = marcaRepository.save(marca);

        Producto producto = new Producto();
        producto.setNombre("Producto de pedido " + identificador);
        producto.setDescripcion("Producto de prueba transaccional");
        producto.setPrecio(new BigDecimal("10.50"));
        producto.setStock(stock);
        producto.setMarca(marca);
        producto.setCategoria(categoria);
        return productoRepository.save(producto);
    }
}
