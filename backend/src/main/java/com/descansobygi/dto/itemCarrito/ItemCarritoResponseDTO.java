package com.descansobygi.dto.itemCarrito;
import com.descansobygi.model.ItemCarrito;

import java.math.BigDecimal;
// devuelve la info de un producto dentro del carrito
public record ItemCarritoResponseDTO(
        Long id,
        Integer cantidad,
        BigDecimal subtotal,
        Long carritoId,
        Long productoId,
        String productoNombre,
        BigDecimal productoUnitario
) {
    public static ItemCarritoResponseDTO fromEntidad(ItemCarrito item) {
        return new ItemCarritoResponseDTO(
                item.getId(),
                item.getCantidad(),
                item.getSubtotal(),
                item.getCarrito().getId(),
                item.getProducto().getId(),
                item.getProducto().getNombre(),
                item.getProducto().getPrecio()
        );
    }
}
