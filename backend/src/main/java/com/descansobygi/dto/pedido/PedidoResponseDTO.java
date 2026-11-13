package com.descansobygi.dto.pedido;

import com.descansobygi.enums.FormaDePago;
import com.descansobygi.dto.detallePedido.DetallePedidoResponseDTO;
import com.descansobygi.model.Pedido;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

// Devuelve el resumen y las líneas históricas del pedido, incluyendo el precio guardado al comprar.

public record PedidoResponseDTO(
        Long id,
        BigDecimal total,
        FormaDePago formaDePago,
        Long usuarioId,
        LocalDateTime fechaAlta,
        boolean cancelado,
        List<DetallePedidoResponseDTO> detalles
) {

    public static PedidoResponseDTO fromEntidad(Pedido pedido) {
        return new PedidoResponseDTO(
                pedido.getId(),
                pedido.getTotal(),
                pedido.getFormaDePago(),
                pedido.getUsuario().getId(),
                pedido.getFechaAlta(),
                pedido.isEliminado(),
                pedido.getDetalles().stream()
                        .filter(detalle -> !detalle.isEliminado())
                        .map(DetallePedidoResponseDTO::fromEntidad)
                        .toList()
        );
    }
}
