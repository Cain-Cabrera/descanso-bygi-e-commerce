package com.descansobygi.dto.itemCarrito;

import com.descansobygi.model.Carrito;
import com.descansobygi.model.ItemCarrito;
import com.descansobygi.model.Producto;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

// recibe los datos necesarios para agregar un procucto al carrito

public record ItemCarritoRequestDTO(
        // la cantidad debe ser mayor que 0
        @NotNull(message = "La cantidad es obligatoria")
        @Positive(message = "La cantidad debe ser mayor a 0")
        Integer cantidad,

        // identifica el carrito al que se agregara el producto
        @NotNull(message = "El id del carrito es obligatorio")
        Long carritoId,

        //identifica el producto que se va agregar
        @NotNull(message = "El id del producto es obligatorio")
        Long productoId


) {
        public ItemCarrito toEntidad(Carrito carrito, Producto producto) {
                ItemCarrito item = new ItemCarrito();
                item.setCantidad(this.cantidad());
                item.setCarrito(carrito);
                item.setProducto(producto);
                // El subtotal se calcula en el Service: cantidad * precio
                item.setSubtotal(producto.getPrecio().multiply(new BigDecimal(this.cantidad())));
                return item;
        }
}
