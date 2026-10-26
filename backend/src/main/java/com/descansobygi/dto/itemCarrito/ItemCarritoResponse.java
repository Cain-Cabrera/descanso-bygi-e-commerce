package com.descansobygi.dto.itemCarrito;
import java.math.BigDecimal;
// devuelve la info de un producto dentro del carrito
public record ItemCarritoResponse(
        Long id,
        Integer cantidad,
        BigDecimal subtotal,
        Long carritoId,
        Long productoId,
        String productoNombre,
        BigDecimal productoUnitario
) {
}
