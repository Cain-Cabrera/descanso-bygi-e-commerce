package com.descansobygi.dto.carrito;

import com.descansobygi.enums.EstadoCarrito;
import com.descansobygi.model.Carrito;

// devuelve los datos del carrito del cliente
public record CarritoResponseDTO(
        Long id,
        EstadoCarrito estado,
        Long usuarioId
) {
    public static CarritoResponseDTO fromEntidad(Carrito carrito) {
        return new CarritoResponseDTO(
                carrito.getId(),
                carrito.getEstado(),
                carrito.getUsuario().getId()
        );
    }
}
