package com.descansobygi.service.carrito;

import com.descansobygi.dto.carrito.CarritoResponseDTO;
import com.descansobygi.dto.itemCarrito.ItemCarritoRequestDTO;
import com.descansobygi.dto.itemCarrito.ItemCarritoResponseDTO;
import com.descansobygi.enums.EstadoCarrito;
import com.descansobygi.exception.BadRequestException;
import com.descansobygi.exception.ResourceNotFoundException;
import com.descansobygi.model.Carrito;
import com.descansobygi.model.ItemCarrito;
import com.descansobygi.model.Producto;
import com.descansobygi.model.Usuario;
import com.descansobygi.repository.carrito.CarritoRepository;
import com.descansobygi.repository.carrito.ItemCarritoRepository;
import com.descansobygi.repository.catalogo.ProductoRepository;
import com.descansobygi.repository.usuarios.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
@Service
public class CarritoServiceImpl  implements ICarritoService{

    @Autowired
    private CarritoRepository carritoRepository;

    @Autowired
    private ItemCarritoRepository itemCarritoRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Transactional(readOnly = true)
    @Override
    public CarritoResponseDTO obtenerCarritoActivo(Long usuarioId) {
        Carrito carrito = obtenerCarritoActivoOThrow(usuarioId);
        return CarritoResponseDTO.fromEntidad(carrito);
    }

    @Transactional
    @Override
    public ItemCarritoResponseDTO agregarAlCarrito(Long usuarioId, ItemCarritoRequestDTO dto) {
        Carrito carrito = obtenerCarritoActivoOThrow(usuarioId);

        Producto producto = productoRepository.findById(dto.productoId())
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado"));

        if (producto.getStock() < dto.cantidad()) {
            throw new BadRequestException("Stock insuficiente");
        }

        ItemCarrito item = dto.toEntidad(carrito, producto);
        return ItemCarritoResponseDTO.fromEntidad(itemCarritoRepository.save(item));
    }

    @Transactional
    @Override
    public ItemCarritoResponseDTO modificarCantidad(Long usuarioId, Long itemId, Integer nuevaCantidad) {
        ItemCarrito item = obtenerItemDelUsuarioOThrow(usuarioId, itemId);

        if (nuevaCantidad <= 0) {
            throw new BadRequestException("La cantidad debe ser mayor a 0");
        }

        if (item.getProducto().getStock() < nuevaCantidad) {
            throw new BadRequestException("Stock insuficiente");
        }

        item.setCantidad(nuevaCantidad);
        item.setSubtotal(item.getProducto().getPrecio().multiply(new BigDecimal(nuevaCantidad)));

        return ItemCarritoResponseDTO.fromEntidad(itemCarritoRepository.save(item));
    }

    @Transactional
    @Override
    public void eliminarDelCarrito(Long usuarioId, Long itemId) {
        ItemCarrito item = obtenerItemDelUsuarioOThrow(usuarioId, itemId);
        itemCarritoRepository.delete(item);
    }

    @Transactional
    @Override
    public void vaciarCarrito(Long usuarioId) {
        Carrito carrito = obtenerCarritoActivoOThrow(usuarioId);
        carrito.getItems().clear();
        carritoRepository.save(carrito);
    }

    // --- Métodos privados ---

    private Carrito obtenerCarritoActivoOThrow(Long usuarioId) {
        return carritoRepository.findByUsuarioIdAndEstado(usuarioId, EstadoCarrito.ACTIVO)
                .orElseThrow(() -> new ResourceNotFoundException("Carrito activo no encontrado"));
    }

    private ItemCarrito obtenerItemDelUsuarioOThrow(Long usuarioId, Long itemId) {
        ItemCarrito item = itemCarritoRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("Item no encontrado"));

        // Verifica que el item pertenezca al carrito activo del usuario
        if (!item.getCarrito().getUsuario().getId().equals(usuarioId)) {
            throw new ResourceNotFoundException("Item no encontrado");
        }
        return item;
    }
}
