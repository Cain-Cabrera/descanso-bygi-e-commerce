package com.descansobygi.integration;

import com.descansobygi.dto.itemCarrito.ItemCarritoRequestDTO;
import com.descansobygi.dto.productos.ProductoEditDTO;
import com.descansobygi.enums.EstadoCarrito;
import com.descansobygi.enums.Rol;
import com.descansobygi.model.Carrito;
import com.descansobygi.model.Categoria;
import com.descansobygi.model.Imagen;
import com.descansobygi.model.ItemCarrito;
import com.descansobygi.model.Marca;
import com.descansobygi.model.Producto;
import com.descansobygi.model.Usuario;
import com.descansobygi.repository.carrito.CarritoRepository;
import com.descansobygi.repository.carrito.ItemCarritoRepository;
import com.descansobygi.repository.catalogo.CategoriaRepository;
import com.descansobygi.repository.catalogo.MarcaRepository;
import com.descansobygi.repository.catalogo.ProductoRepository;
import com.descansobygi.repository.usuarios.UsuarioRepository;
import com.descansobygi.repository.shared.ImagenRepository;
import com.descansobygi.service.carrito.ICarritoService;
import com.descansobygi.service.catalogo.IProductoService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
class PersistenciaCarritoYProductoTest {

    @Autowired
    private ICarritoService carritoService;

    @Autowired
    private IProductoService productoService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CarritoRepository carritoRepository;

    @Autowired
    private ItemCarritoRepository itemCarritoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private MarcaRepository marcaRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private ImagenRepository imagenRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    void operacionesDelCarritoSePersistenYAcumulanProductosDuplicados() {
        Usuario usuario = crearUsuario();
        Producto producto = crearProducto("Almohada de prueba", 5);
        Carrito carrito = new Carrito();
        carrito.setEstado(EstadoCarrito.ACTIVO);
        carrito.setUsuario(usuario);
        carrito = carritoRepository.save(carrito);

        // Agregar el mismo producto dos veces actualiza un único ítem activo.
        var primeraRespuesta = carritoService.agregarAlCarrito(usuario.getId(),
                new ItemCarritoRequestDTO(2, producto.getId()));
        var segundaRespuesta = carritoService.agregarAlCarrito(usuario.getId(),
                new ItemCarritoRequestDTO(1, producto.getId()));

        assertEquals(primeraRespuesta.id(), segundaRespuesta.id());
        entityManager.flush();
        entityManager.clear();

        ItemCarrito guardado = itemCarritoRepository.findById(segundaRespuesta.id()).orElseThrow();
        assertEquals(3, guardado.getCantidad());
        assertEquals(0, new BigDecimal("31.50").compareTo(guardado.getSubtotal()));

        // Modificar la cantidad persiste tanto cantidad como subtotal.
        carritoService.modificarCantidad(usuario.getId(), guardado.getId(), 4);
        entityManager.flush();
        entityManager.clear();
        guardado = itemCarritoRepository.findById(guardado.getId()).orElseThrow();
        assertEquals(4, guardado.getCantidad());
        assertEquals(0, new BigDecimal("42.00").compareTo(guardado.getSubtotal()));

        // Quitar un producto conserva la fila histórica con eliminado=true.
        carritoService.eliminarDelCarrito(usuario.getId(), guardado.getId());
        entityManager.flush();
        entityManager.clear();
        ItemCarrito quitado = itemCarritoRepository.findById(guardado.getId()).orElseThrow();
        assertTrue(quitado.isEliminado());

        // Volver a agregar crea un ítem activo nuevo; vaciar lo marca como eliminado.
        var nuevoItem = carritoService.agregarAlCarrito(usuario.getId(),
                new ItemCarritoRequestDTO(1, producto.getId()));
        assertNotEquals(guardado.getId(), nuevoItem.id());
        carritoService.vaciarCarrito(usuario.getId());
        entityManager.flush();
        entityManager.clear();

        assertTrue(itemCarritoRepository.findByCarritoIdAndEliminadoFalse(carrito.getId()).isEmpty());
        assertTrue(itemCarritoRepository.findById(nuevoItem.id()).orElseThrow().isEliminado());
        assertFalse(carritoRepository.findByUsuarioIdAndEstadoAndEliminadoFalse(
                usuario.getId(), EstadoCarrito.ACTIVO).orElseThrow().isEliminado());
    }

    @Test
    void reemplazarImagenesBorraLasFilasAnterioresDePostgres() {
        Producto producto = crearProducto("Colchón de prueba", 2);
        producto.getImagenes().add(crearImagen(producto, "https://ejemplo.com/antes-1.jpg"));
        producto.getImagenes().add(crearImagen(producto, "https://ejemplo.com/antes-2.jpg"));
        productoRepository.save(producto);
        entityManager.flush();
        List<Long> idsAnteriores = producto.getImagenes().stream().map(Imagen::getId).toList();

        entityManager.clear();
        productoService.modificar(producto.getId(), new ProductoEditDTO(
                null, null, null, null, null, null,
                List.of("https://ejemplo.com/nueva.jpg")));
        entityManager.flush();
        entityManager.clear();

        assertTrue(idsAnteriores.stream().allMatch(id -> imagenRepository.findById(id).isEmpty()));
        List<Imagen> imagenesActivasEnBase = imagenRepository.findAll().stream()
                .filter(imagen -> imagen.getProducto().getId().equals(producto.getId()))
                .toList();
        assertEquals(1, imagenesActivasEnBase.size());
        assertEquals("https://ejemplo.com/nueva.jpg", imagenesActivasEnBase.getFirst().getUrl());
    }

    private Usuario crearUsuario() {
        String identificador = UUID.randomUUID().toString();
        Usuario usuario = new Usuario();
        usuario.setNombre("Usuario de prueba");
        usuario.setApellido("Integración");
        usuario.setMail("test-" + identificador + "@ejemplo.com");
        usuario.setContrasena("no-utilizable-en-login");
        usuario.setRol(Rol.USUARIO);
        return usuarioRepository.save(usuario);
    }

    private Producto crearProducto(String nombre, int stock) {
        String identificador = UUID.randomUUID().toString();
        Categoria categoria = new Categoria();
        categoria.setNombre("Categoría " + identificador);
        categoria = categoriaRepository.save(categoria);

        Marca marca = new Marca();
        marca.setNombre("Marca " + identificador);
        marca = marcaRepository.save(marca);

        Producto producto = new Producto();
        producto.setNombre(nombre);
        producto.setDescripcion("Producto creado por una prueba transaccional");
        producto.setPrecio(new BigDecimal("10.50"));
        producto.setStock(stock);
        producto.setMarca(marca);
        producto.setCategoria(categoria);
        return productoRepository.save(producto);
    }

    private Imagen crearImagen(Producto producto, String url) {
        Imagen imagen = new Imagen();
        imagen.setProducto(producto);
        imagen.setUrl(url);
        return imagen;
    }
}
