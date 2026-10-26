package com.descansobygi.dto.pedido;

import com.descansobygi.enums.FormaDePago;
import com.descansobygi.model.Pedido;
import com.descansobygi.model.Usuario;

import java.math.BigDecimal;

// devuelve los datos principales de un pedido

public record PedidoResponseDTO(
        Long id,
        BigDecimal total,
        FormaDePago formaDePago,
        Long usuarioId
) {

    public static PedidoResponseDTO fromEntidad(Pedido pedido) {
        return new PedidoResponseDTO(
                pedido.getId(),
                pedido.getTotal(),
                pedido.getFormaDePago(),
                pedido.getUsuario().getId()
        );
    }
}
