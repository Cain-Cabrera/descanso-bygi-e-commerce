package com.descansobygi.dto;

import java.math.BigDecimal;
import java.nio.Buffer;

// devuelve la informacion historica de un produco

public record DetallePedidoResponseDTO(
        Long id,
        Integer cantidad,
        BigDecimal precioUnitario,
        BigDecimal subtotal,
        Long pedidoId,
        Long productoId,
        String productoNombre
) {
}
