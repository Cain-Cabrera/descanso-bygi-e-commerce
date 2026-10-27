package com.descansobygi.dto.productos;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.List;

public record ProductoEditDTO(
        @Size(max = 100, message = "El nombre no puede superar los 100 caracteres")
        String nombre,

        @Size(max = 255, message = "La descripción no puede superar los 255 caracteres")
        String descripcion,

        @DecimalMin(value = "0.0", message = "El precio no puede ser negativo")
        BigDecimal precio,

        @PositiveOrZero(message = "El stock no puede ser negativo")
        Integer stock,

        Long marcaId,

        Long categoriaId,

        @Size(max = 10, message = "Un producto no puede tener más de 10 imágenes")
        List<@NotBlank(message = "La URL de la imagen no puede estar vacía") String> imagenes
) {
}
