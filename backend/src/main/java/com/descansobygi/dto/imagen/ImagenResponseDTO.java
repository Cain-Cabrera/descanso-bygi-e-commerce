package com.descansobygi.dto.imagen;

import com.descansobygi.model.Imagen;

// devuelve los datos de una imagen asociada a un producto
public record ImagenResponseDTO(
        Long id,
        String url,
        Long productoId
) {
    public static ImagenResponseDTO fromEntidad(Imagen imagen) {
        return new ImagenResponseDTO(
                imagen.getId(),
                imagen.getUrl(),
                imagen.getProducto().getId()
        );
    }
}
