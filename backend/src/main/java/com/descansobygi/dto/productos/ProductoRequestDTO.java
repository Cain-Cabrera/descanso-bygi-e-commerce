package com.descansobygi.dto.productos;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

// recibe lso datos necesarios para crear o modificar un producto

public record ProductoRequestDTO(

        // el nombre obligatorio y hasta 20 chars
        @NotBlank(message = "El nombre es obligatorio")
        @Size(max = 20, message = "El nombre no puede superar lso 20 caracteres")
        String nombre,

        // descripcion opcional pero recomendado, hasta 100 chars
        @Size(max = 100, message = "La descripcion no puede superar los 100 caracteres")
        String descripcion,

        // el precio obligatorio y no puede ser negativo
        @NotNull(message = "EL precio es obligatorio")
        @DecimalMin(value = "0.0", message = "El precio no puede ser negativo")
        BigDecimal precio,

        // stock obligatorio y debe ser mayor a 0
        @NotNull(message = "El stock es obligatorio")
        @PositiveOrZero(message = "El stock no puede ser negativo")
        Integer stock,

        // recibimos los id de las relaciones
        @NotNull(message = "La marca es obligatorio")
        Long marcaId,

        @NotNull(message = "La categoria es obligatoria")
        Long categoriaId
) {
}
