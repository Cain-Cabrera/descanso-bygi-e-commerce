package com.descansobygi.dto.usuarios;

import com.descansobygi.enums.Rol;
import jakarta.validation.constraints.*;


// dto para recibir los datos de un usuario
// al registrarlo o modificarlo
public record UsuarioRequestDTO (

    // el nombre es obligatorio y admite hasta 100 chars
    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no puede superar los 100 caracteres")
    String nombre,

    // El apellido es oblogatorio y adminte tambien hasta 100
    @NotBlank(message = "El apellido es obligatorio")
    @Size(max = 100, message = "El apellido no puede superar los 100 caracteres")
    String apellido,

    // El correo es oblogatorio para el registro, y debe tener formato valido y unico
    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "El correo electronico no tiene un formato valido")
    @Size(max = 150, message = "El correo no puede superar los 150 caracteres")
    String mail,

    // password se recibe, pero no se devuelve en el dto de respuesta
    @NotBlank(message = "La contrasena es obligatoria")
    @Size(min = 4, max = 10, message = "La contrasena debe tenre entre 4 y 10 caracteres")
    String contrasena,

    // celular opcional, pero tiene un maximo de 20 caracteres
    @Size(max = 30, message = "El celular no puede superar los 20 caracteres")
    String celular,

    // el rol debe ser uno de los valores dinificos en la enumeracion
    @NotNull(message = "Rol oblogatorio")
    Rol rol

) {

}



