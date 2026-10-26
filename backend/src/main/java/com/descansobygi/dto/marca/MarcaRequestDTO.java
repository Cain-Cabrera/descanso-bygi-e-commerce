package com.descansobygi.dto.marca;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

// recibe los datos necesarios para crear o modificar una marca

public record MarcaRequestDTO(

        // el nombre es obligatorio y admite hasta 20 caracteres
        @NotBlank(message = "El nombre de la marca es obligatorio")
        @Size(max = 20, message = "El nombre no puede superar los 20 caracteres")
        String nombre
) {
}
