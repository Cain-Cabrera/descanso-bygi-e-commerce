package com.descansobygi.dto.carrito;

import com.descansobygi.dto.itemCarrito.ItemCarritoResponseDTO;
import com.descansobygi.enums.EstadoCarrito;
import com.descansobygi.model.Carrito;

import java.util.List;

// devuelve los datos del carrito del cliente
public record CarritoResponseDTO(
        Long id,
        EstadoCarrito estado,
        Long usuarioId,
        List<ItemCarritoResponseDTO> items
) {
    public static CarritoResponseDTO fromEntidad(Carrito carrito) {
        return new CarritoResponseDTO(
                carrito.getId(),
                carrito.getEstado(),
                carrito.getUsuario().getId(),
                carrito.getItems().stream()
                        .map(ItemCarritoResponseDTO::fromEntidad)
                        .toList()
        );
    }
}
