package com.descansobygi.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

// va recibir los datos necesarios para crear o modificar una categoria
public record CategoriaRequestDTO(
        // el nombre es obligatorio y admite hasta 30 caracteres
        @NotBlank(message = "El nombre de la categoria es oblogatorio")
        @Size(max = 30, message = "No debe superar los 30 caracteres")
        String nombre,

        // la descripcion es opcional pero recomendada, admite hasta 100 caracteres
        @Size(max = 100, message = "La descripcion no debe superar los 100 caracteres")
        String descripcion
) {
}
