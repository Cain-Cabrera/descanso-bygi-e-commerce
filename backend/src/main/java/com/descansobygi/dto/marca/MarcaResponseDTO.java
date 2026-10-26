package com.descansobygi.dto.marca;


import com.descansobygi.model.Marca;

// devuelve los datos de la marca al cliente
public record MarcaResponseDTO(
        Long id,
        String nombre
) {
    public static MarcaResponseDTO fromEntidad(Marca marca) {
        return new MarcaResponseDTO(
                marca.getId(),
                marca.getNombre()
        );
    }
}
