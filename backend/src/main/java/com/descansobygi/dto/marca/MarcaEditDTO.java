package com.descansobygi.dto.marca;

import jakarta.validation.constraints.Size;

public record MarcaEditDTO(
        @Size(max = 100, message = "El nombre no puede superar los 100 caracteres")
        String nombre
) {
}
