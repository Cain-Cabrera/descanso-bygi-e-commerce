
package com.descansobygi.dto.login;

import com.descansobygi.enums.Rol;
import com.descansobygi.model.Usuario;

// Respuesta del login: el token JWT y los datos públicos del usuario autenticado.
public record LoginResponseDTO(
        String token,
        String tipo,        // siempre "Bearer": así se envía en el header Authorization
        long expiraEn,      // segundos de validez del token
        Long id,
        String nombre,
        String apellido,
        String mail,
        Rol rol
) {
    public static LoginResponseDTO of(String token, long expiraEn, Usuario usuario) {
        return new LoginResponseDTO(
                token,
                "Bearer",
                expiraEn,
                usuario.getId(),
                usuario.getNombre(),
                usuario.getApellido(),
                usuario.getMail(),
                usuario.getRol()
        );
    }
}
