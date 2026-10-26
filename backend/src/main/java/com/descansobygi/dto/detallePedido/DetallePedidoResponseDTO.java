package com.descansobygi.dto.detallePedido;

import com.descansobygi.model.DetallePedido;
import com.descansobygi.model.Pedido;
import com.descansobygi.model.Producto;

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

    public static DetallePedidoResponseDTO fromEntidad(DetallePedido detalle) {
        return new DetallePedidoResponseDTO(
                detalle.getId(),
                detalle.getCantidad(),
                detalle.getPrecioUnitario(),
                detalle.getSubtotal(),
                detalle.getPedido().getId(),
                detalle.getProducto().getId(),
                detalle.getProducto().getNombre()
        );
    }
}
