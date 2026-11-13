package com.descansobygi.dto.categorias;


import com.descansobygi.model.Categoria;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

// va recibir los datos necesarios para crear o modificar una categoria
public record CategoriaRequestDTO(
        // Mantiene el mismo límite que la entidad Categoria.
        @NotBlank(message = "El nombre de la categoria es oblogatorio")
        @Size(max = 100, message = "No debe superar los 100 caracteres")
        String nombre,

        // Mantiene el mismo límite que la columna descripcion.
        @Size(max = 255, message = "La descripcion no debe superar los 255 caracteres")
        String descripcion
) {
        public Categoria toEntidad() {
                Categoria categoria = new Categoria();
                categoria.setNombre(this.nombre());
                categoria.setDescripcion(this.descripcion());
                return categoria;
        }
}
