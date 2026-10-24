package com.descansobygi.dto;
import java.math.BigDecimal;

// devuelve los datos de un producto al cliente

public record ProductoResponseDTO(
        Long id,
        String nombre,
        String descripcion,
        BigDecimal precio,
        Integer stock,
        Long marcaId,
        String marcaNombre,
        Long categoriaId,
        String categoriaNombre
) {
}
