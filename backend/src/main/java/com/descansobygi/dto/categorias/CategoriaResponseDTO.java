package com.descansobygi.dto.categorias;


import com.descansobygi.model.Categoria;

// va devolver los datos de una categoria al cliente
public record CategoriaResponseDTO(
        Long id,
        String nombre,
        String descripcion
) {
    public static CategoriaResponseDTO fromEntidad(Categoria categoria) {
        return new CategoriaResponseDTO(
                categoria.getId(),
                categoria.getNombre(),
                categoria.getDescripcion()
        );
    }
}
