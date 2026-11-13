package com.descansobygi.dto.marca;

import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;

public record MarcaEditDTO(
        @Pattern(regexp = ".*\\S.*", message = "El nombre no puede estar vacío")
        @Size(max = 100, message = "El nombre no puede superar los 100 caracteres")
        String nombre
) {
}
