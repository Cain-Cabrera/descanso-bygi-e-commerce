package com.descansobygi.dto.marca;

import com.descansobygi.model.Marca;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

// recibe los datos necesarios para crear o modificar una marca

public record MarcaRequestDTO(

        // Mantiene el mismo límite que la columna nombre de Marca.
        @NotBlank(message = "El nombre de la marca es obligatorio")
        @Size(max = 100, message = "El nombre no puede superar los 100 caracteres")
        String nombre
) {

        public Marca toEntidad() {
                Marca marca = new Marca();
                marca.setNombre(this.nombre());
                return marca;
        }
}
