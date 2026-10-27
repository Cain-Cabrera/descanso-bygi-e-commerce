package com.descansobygi.dto.categorias;

import com.descansobygi.model.Categoria;
import jakarta.validation.constraints.Size;

public record CategoriaEditDTO(
        @Size(max = 100, message = "El nombre no puede superar los 100 caracteres")
        String nombre,

        @Size(max = 255, message = "La descripción no puede superar los 255 caracteres")
        String descripcion
) {
    // Copia solo los campos que llegaron (no nulos) a la entidad existente
    public void aplicarA(Categoria categoria) {
        if (this.nombre() != null) {
            categoria.setNombre(this.nombre());
        }
        if (this.descripcion() != null) {
            categoria.setDescripcion(this.descripcion());
        }
    }
}
