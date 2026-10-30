package com.descansobygi.service.auth;

import com.descansobygi.dto.login.LoginRequestDTO;
import com.descansobygi.dto.login.LoginResponseDTO;

public interface IAuthService {

    // Verifica las credenciales y devuelve el token JWT con los datos del usuario.
    LoginResponseDTO login(LoginRequestDTO dto);
}
