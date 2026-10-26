package com.descansobygi.dto.usuarios;

import com.descansobygi.enums.Rol;

// dto para devolver los datos publicos de un usuario
// no incluye por ejemplo la password
public record UsuarioReponseDTO (
    Long id,
    String nombre,
    String apellido,
    String mail,
    String celular,
    Rol rol
) {

}
