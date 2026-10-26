package com.descansobygi.dto.itemCarrito;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

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
}
