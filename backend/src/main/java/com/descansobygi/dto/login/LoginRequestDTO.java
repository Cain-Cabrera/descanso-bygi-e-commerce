package com.descansobygi.dto.login;


import com.descansobygi.model.Usuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

// recibe las credenciales para inciar sesion

public record LoginRequestDTO(
        @NotBlank(message = "El correo es obligatorio")
        @Email(message = "El correo electronico no tiene un formato valido")
        @Size(max = 150, message = "El correo no puede superar los 150 caracteres")
        String mail,

        @NotBlank(message = "La contrasena es obligatoria")
        @Size(max = 72, message = "La contrasena no puede superar los 72 caracteres")
        String contrasena
){
        public static LoginResponseDTO fromEntidad(Usuario usuario) {
                return new LoginResponseDTO(
                        usuario.getId(),
                        usuario.getNombre(),
                        usuario.getApellido(),
                        usuario.getMail(),
                        usuario.getRol()
                );
        }
}
