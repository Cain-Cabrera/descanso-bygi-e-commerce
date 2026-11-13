package com.descansobygi.service.pedidos;

import com.descansobygi.dto.pedido.PedidoRequestDTO;
import com.descansobygi.dto.pedido.PedidoResponseDTO;
import com.descansobygi.enums.EstadoCarrito;
import com.descansobygi.exception.BadRequestException;
import com.descansobygi.exception.ResourceNotFoundException;
import com.descansobygi.model.*;
import com.descansobygi.repository.carrito.CarritoRepository;
import com.descansobygi.repository.carrito.ItemCarritoRepository;
import com.descansobygi.repository.catalogo.ProductoRepository;
import com.descansobygi.repository.pedidos.DetallePedidoRepository;
import com.descansobygi.repository.pedidos.PedidoRepository;
import com.descansobygi.repository.usuarios.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
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
    private ItemCarritoRepository itemCarritoRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Transactional
    @Override
    public PedidoResponseDTO crearPedido(Long usuarioId, PedidoRequestDTO dto) {
        // Buscar usuario
        Usuario usuario = usuarioRepository.findByIdAndEliminadoFalse(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        // Obtener carrito activo
        Carrito carrito = carritoRepository.findByUsuarioIdAndEstadoAndEliminadoFalse(usuarioId, EstadoCarrito.ACTIVO)
                .orElseThrow(() -> new ResourceNotFoundException("Carrito no encontrado"));

        List<ItemCarrito> items = carrito.getItems().stream()
                .filter(item -> !item.isEliminado())
                .sorted(java.util.Comparator.comparing(item -> item.getProducto().getId()))
                .toList();
        if (items.isEmpty()) {
            throw new BadRequestException("El carrito está vacío");
        }

        // Bloquear productos durante la validación para que pedidos concurrentes
        // no descuenten las mismas unidades disponibles.
        Map<Long, Producto> productosBloqueados = new LinkedHashMap<>();
        Map<Long, Integer> cantidadesSolicitadas = new LinkedHashMap<>();
        for (ItemCarrito item : items) {
            Long productoId = item.getProducto().getId();
            Producto producto = productoRepository.findActivoPorIdParaActualizar(productoId)
                    .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado"));
            productosBloqueados.put(productoId, producto);
            cantidadesSolicitadas.merge(productoId, item.getCantidad(), Integer::sum);
        }
        for (Map.Entry<Long, Integer> solicitud : cantidadesSolicitadas.entrySet()) {
            Producto producto = productosBloqueados.get(solicitud.getKey());
            if (producto.getStock() < solicitud.getValue()) {
                throw new BadRequestException("Stock insuficiente para el producto " + producto.getNombre());
            }
        }

        // El pedido conserva los precios vigentes al momento de confirmarlo.
        BigDecimal total = BigDecimal.ZERO;
        for (ItemCarrito item : items) {
            Producto producto = productosBloqueados.get(item.getProducto().getId());
            total = total.add(producto.getPrecio().multiply(BigDecimal.valueOf(item.getCantidad())));
        }

        // Crear pedido
        Pedido pedido = dto.toEntidad(usuario, total);
        Pedido pedidoGuardado = pedidoRepository.save(pedido);

        // Crear detalles y reducir stock
        for (ItemCarrito item : items) {
            Producto producto = productosBloqueados.get(item.getProducto().getId());
            DetallePedido detalle = new DetallePedido();
            detalle.setPedido(pedidoGuardado);
            detalle.setProducto(producto);
            detalle.setCantidad(item.getCantidad());
            detalle.setPrecioUnitario(producto.getPrecio());
            detalle.setSubtotal(producto.getPrecio().multiply(BigDecimal.valueOf(item.getCantidad())));

            detallePedidoRepository.save(detalle);
            pedidoGuardado.getDetalles().add(detalle);

            producto.setStock(producto.getStock() - item.getCantidad());
        }

        // Mantener el historial de ítems, quitándolos del carrito activo.
        items.forEach(item -> item.setEliminado(true));
        itemCarritoRepository.saveAll(items);

        return PedidoResponseDTO.fromEntidad(pedidoGuardado);
    }

    @Transactional(readOnly = true)
    @Override
    public PedidoResponseDTO obtenerPorId(Long id, Long usuarioId, boolean esAdmin) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido no encontrado"));

        // Un usuario solo puede ver sus propios pedidos; el administrador ve todos.
        // Respondemos "no encontrado" para no revelar que el pedido existe.
        if (!esAdmin && !pedido.getUsuario().getId().equals(usuarioId)) {
            throw new ResourceNotFoundException("Pedido no encontrado");
        }

        return PedidoResponseDTO.fromEntidad(pedido);
    }

    @Transactional(readOnly = true)
    @Override
    public List<PedidoResponseDTO> obtenerPedidosUsuario(Long usuarioId) {
        return pedidoRepository.findByUsuarioIdAndEliminadoFalseOrderByFechaAltaDesc(usuarioId).stream()
                .map(PedidoResponseDTO::fromEntidad)
                .toList();
    }

    @Transactional
    @Override
    public PedidoResponseDTO cancelarPedido(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido no encontrado"));

        // Un pedido ya cancelado no se puede volver a cancelar:
        // si no, el stock se reintegraría dos veces (RN24, RN29).
        if (pedido.isEliminado()) {
            throw new BadRequestException("El pedido ya fue cancelado");
        }

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

    @Transactional(readOnly = true)
    @Override
    public List<PedidoResponseDTO> listarTodos() {
        // La administración también puede consultar pedidos cancelados y distinguirlos por el indicador cancelado.
        return pedidoRepository.findAllByOrderByFechaAltaDesc().stream()
                .map(PedidoResponseDTO::fromEntidad)
                .toList();
    }
}
