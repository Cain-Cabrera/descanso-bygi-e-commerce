package com.descansobygi.dto.imagen;

// devuelve los datos de una imagen asociada a un producto
public record ImagenResponseDTO(
        Long id,
        String url,
        Long productoId
) {
}
