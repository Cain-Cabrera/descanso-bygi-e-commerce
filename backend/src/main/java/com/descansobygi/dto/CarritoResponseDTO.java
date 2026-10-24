package com.descansobygi.dto;

import com.descansobygi.enums.EstadoCarrito;

// devuelve los datos del carrito del cliente
public record CarritoResponseDTO(
        Long id,
        EstadoCarrito estado,
        Long usuarioId
) {
}
