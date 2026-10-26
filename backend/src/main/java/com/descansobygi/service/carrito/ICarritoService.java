package com.descansobygi.service.carrito;

import com.descansobygi.dto.carrito.CarritoResponseDTO;
import com.descansobygi.dto.itemCarrito.ItemCarritoRequestDTO;
import com.descansobygi.dto.itemCarrito.ItemCarritoResponseDTO;
import org.springframework.transaction.annotation.Transactional;

public interface ICarritoService {
    @Transactional(readOnly = true)
    CarritoResponseDTO obtenerCarritoActivo(Long usuarioId);

    @Transactional
    ItemCarritoResponseDTO agregarAlCarrito(ItemCarritoRequestDTO dto);

    @Transactional
    ItemCarritoResponseDTO modificarCantidad(Long itemId, Integer nuevaCantidad);

    @Transactional
    void eliminarDelCarrito(Long itemId);

    @Transactional
    void vaciarCarrito(Long carritoId);
}
