package com.descansobygi.dto.categorias;


// va devolver los datos de una categoria al cliente
public record CategoriaResponseDTO(
        Long id,
        String nombre,
        String descripcion
) {
}
