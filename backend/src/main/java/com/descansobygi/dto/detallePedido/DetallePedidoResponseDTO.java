package com.descansobygi.dto.detallePedido;

import java.math.BigDecimal;

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
