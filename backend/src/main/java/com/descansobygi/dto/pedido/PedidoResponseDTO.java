package com.descansobygi.dto.pedido;

import com.descansobygi.enums.FormaDePago;
import java.math.BigDecimal;

// devuelve los datos principales de un pedido

public record PedidoResponseDTO(
        Long id,
        BigDecimal total,
        FormaDePago formaDePago,
        Long usuarioId
) {
}
