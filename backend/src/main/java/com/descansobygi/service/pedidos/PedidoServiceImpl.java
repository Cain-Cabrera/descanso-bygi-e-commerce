package com.descansobygi.service.pedidos;

import com.descansobygi.dto.pedido.PedidoRequestDTO;
import com.descansobygi.dto.pedido.PedidoResponseDTO;
import com.descansobygi.enums.EstadoCarrito;
import com.descansobygi.model.*;
import com.descansobygi.repository.carrito.CarritoRepository;
import com.descansobygi.repository.catalogo.ProductoRepository;
import com.descansobygi.repository.pedidos.DetallePedidoRepository;
import com.descansobygi.repository.pedidos.PedidoRepository;
import com.descansobygi.repository.usuarios.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class PedidoServiceImpl implements IPedidoService{
    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private DetallePedidoRepository detallePedidoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CarritoRepository carritoRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Transactional
    @Override
    public PedidoResponseDTO crearPedido(Long usuarioId, PedidoRequestDTO dto) {
        // Buscar usuario
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Obtener carrito activo
        Carrito carrito = carritoRepository.findByUsuarioIdAndEstado(usuarioId, EstadoCarrito.ACTIVO)
                .orElseThrow(() -> new RuntimeException("Carrito no encontrado"));

        // Validar que carrito tenga items
        if (carrito.getItems().isEmpty()) {
            throw new RuntimeException("El carrito está vacío");
        }

        // Calcular total
        BigDecimal total = BigDecimal.ZERO;
        for (ItemCarrito item : carrito.getItems()) {
            total = total.add(item.getSubtotal());
        }

        // Crear pedido
        Pedido pedido = dto.toEntidad(usuario, total);
        Pedido pedidoGuardado = pedidoRepository.save(pedido);

        // Crear detalles y reducir stock
        for (ItemCarrito item : carrito.getItems()) {
            DetallePedido detalle = new DetallePedido();
            detalle.setPedido(pedidoGuardado);
            detalle.setProducto(item.getProducto());
            detalle.setCantidad(item.getCantidad());
            detalle.setPrecioUnitario(item.getProducto().getPrecio());
            detalle.setSubtotal(item.getSubtotal());

            detallePedidoRepository.save(detalle);

            // Reducir stock
            Producto producto = item.getProducto();
            producto.setStock(producto.getStock() - item.getCantidad());
            productoRepository.save(producto);
        }

        // Vaciar carrito
        carrito.getItems().clear();
        carritoRepository.save(carrito);

        return PedidoResponseDTO.fromEntidad(pedidoGuardado);
    }

    @Transactional(readOnly = true)
    @Override
    public PedidoResponseDTO obtenerPorId(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));

        return PedidoResponseDTO.fromEntidad(pedido);
    }

    @Transactional(readOnly = true)
    @Override
    public List<PedidoResponseDTO> obtenerPedidosUsuario(Long usuarioId) {
        return pedidoRepository.findByUsuarioIdAndEliminadoFalse(usuarioId).stream()
                .map(PedidoResponseDTO::fromEntidad)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public PedidoResponseDTO cancelarPedido(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));

        // Devolver stock
        for (DetallePedido detalle : pedido.getDetalles()) {
            Producto producto = detalle.getProducto();
            producto.setStock(producto.getStock() + detalle.getCantidad());
            productoRepository.save(producto);
        }


        // Marcar como eliminado
        pedido.setEliminado(true);
        return PedidoResponseDTO.fromEntidad(pedidoRepository.save(pedido));
    }
}
