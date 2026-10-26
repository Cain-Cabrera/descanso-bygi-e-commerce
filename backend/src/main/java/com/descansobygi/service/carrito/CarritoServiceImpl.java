package com.descansobygi.service.carrito;

import com.descansobygi.dto.carrito.CarritoResponseDTO;
import com.descansobygi.dto.itemCarrito.ItemCarritoRequestDTO;
import com.descansobygi.dto.itemCarrito.ItemCarritoResponseDTO;
import com.descansobygi.enums.EstadoCarrito;
import com.descansobygi.model.Carrito;
import com.descansobygi.model.ItemCarrito;
import com.descansobygi.model.Producto;
import com.descansobygi.model.Usuario;
import com.descansobygi.repository.carrito.CarritoRepository;
import com.descansobygi.repository.carrito.ItemCarritoRepository;
import com.descansobygi.repository.catalogo.ProductoRepository;
import com.descansobygi.repository.usuarios.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

public class CarritoServiceImpl  implements ICarritoService{

    @Autowired
    private CarritoRepository carritoRepository;

    @Autowired
    private ItemCarritoRepository itemCarritoRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional(readOnly = true)
    @Override
    public CarritoResponseDTO obtenerCarritoActivo(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Carrito carrito = carritoRepository.findByUsuarioIdAndEstado(usuarioId, EstadoCarrito.ACTIVO)
                .orElseThrow(() -> new RuntimeException("Carrito activo no encontrado"));

        return CarritoResponseDTO.fromEntidad(carrito);
    }

    @Transactional
    @Override
    public ItemCarritoResponseDTO agregarAlCarrito(ItemCarritoRequestDTO dto) {
        Carrito carrito = carritoRepository.findById(dto.carritoId())
                .orElseThrow(() -> new RuntimeException("Carrito no encontrado"));

        Producto producto = productoRepository.findById(dto.productoId())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        // Validar stock
        if (producto.getStock() < dto.cantidad()) {
            throw new RuntimeException("Stock insuficiente");
        }

        ItemCarrito item = dto.toEntidad(carrito, producto);
        ItemCarrito itemGuardado = itemCarritoRepository.save(item);

        return ItemCarritoResponseDTO.fromEntidad(itemGuardado);
    }


    @Override
    public ItemCarritoResponseDTO modificarCantidad(Long itemId, Integer nuevaCantidad) {
        ItemCarrito item = itemCarritoRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item no encontrado"));

        // Validar cantidad
        if (nuevaCantidad <= 0) {
            throw new RuntimeException("Cantidad debe ser mayor a 0");
        }

        // Validar stock
        if (item.getProducto().getStock() < nuevaCantidad) {
            throw new RuntimeException("Stock insuficiente");
        }

        item.setCantidad(nuevaCantidad);
        item.setSubtotal(item.getProducto().getPrecio().multiply(new BigDecimal(nuevaCantidad)));

        ItemCarrito itemActualizado = itemCarritoRepository.save(item);
        return ItemCarritoResponseDTO.fromEntidad(itemActualizado);
    }

    @Override
    public void eliminarDelCarrito(Long itemId) {
        ItemCarrito item = itemCarritoRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item no encontrado"));

        itemCarritoRepository.delete(item);
    }

    @Override
    public void vaciarCarrito(Long carritoId) {
        Carrito carrito = carritoRepository.findById(carritoId)
                .orElseThrow(() -> new RuntimeException("Carrito no encontrado"));

        carrito.getItems().clear();
        carritoRepository.save(carrito);
    }
}
