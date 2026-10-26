package com.descansobygi.dto.usuarios;

import com.descansobygi.enums.Rol;
import com.descansobygi.model.Usuario;

// dto para devolver los datos publicos de un usuario
// no incluye por ejemplo la password
public record UsuarioResponseDTO(
    Long id,
    String nombre,
    String apellido,
    String mail,
    String celular

) {
    public static UsuarioResponseDTO fromEntidad(Usuario usuario) {
        return new UsuarioResponseDTO(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getApellido(),
                usuario.getMail(),
                usuario.getCelular()
        );
    }
}
