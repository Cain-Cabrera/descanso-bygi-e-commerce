package com.descansobygi.dto.productos;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;

import java.math.BigDecimal;
import java.util.List;

public record ProductoEditDTO(
        @Pattern(regexp = ".*\\S.*", message = "El nombre no puede estar vacío")
        @Size(max = 150, message = "El nombre no puede superar los 150 caracteres")
        String nombre,

        @Size(max = 500, message = "La descripción no puede superar los 500 caracteres")
        String descripcion,

        @DecimalMin(value = "0.0", message = "El precio no puede ser negativo")
        BigDecimal precio,

        @PositiveOrZero(message = "El stock no puede ser negativo")
        Integer stock,

        Long marcaId,

        Long categoriaId,

        // Un producto puede tener cero o varias imágenes; cada URL respeta el diccionario de datos.
        List<@NotBlank(message = "La URL de la imagen no puede estar vacía")
                @Size(max = 500, message = "La URL de la imagen no puede superar los 500 caracteres") String> imagenes
) {
}
