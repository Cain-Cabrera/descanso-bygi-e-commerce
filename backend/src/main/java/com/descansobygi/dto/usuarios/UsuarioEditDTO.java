package com.descansobygi.dto.usuarios;

import com.descansobygi.model.Usuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UsuarioEditDTO(

        @Size(max = 100, message = "El nombre no puede superar los 100 caracteres")
        String nombre,


        @Size(max = 100, message = "El apellido no puede superar los 100 caracteres")
        String apellido,


        @Email(message = "El correo electrónico no tiene un formato válido")
        @Size(max = 150, message = "El correo no puede superar los 150 caracteres")
        String mail,

        @Size(max = 30, message = "El celular no puede superar los 30 caracteres")
        String celular
) {
    // Copia los datos del DTO a la entidad existente (no toca contraseña ni rol)
    public void aplicarA(Usuario usuario) {
        usuario.setNombre(this.nombre());
        usuario.setApellido(this.apellido());
        usuario.setMail(this.mail());
        usuario.setCelular(this.celular());
    }
}
