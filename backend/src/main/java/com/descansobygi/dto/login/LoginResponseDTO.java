
package com.descansobygi.dto.login;

import com.descansobygi.enums.Rol;

// devuelve lso datos publicos del usuario autentificado
public record LoginResponseDTO(
        Long id,
        String nombre,
        String apellido,
        String mail,
        Rol rol
) {}
