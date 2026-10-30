package com.descansobygi.service.auth;

import com.descansobygi.dto.login.LoginRequestDTO;
import com.descansobygi.dto.login.LoginResponseDTO;
import com.descansobygi.exception.InvalidCredentialsException;
import com.descansobygi.model.Usuario;
import com.descansobygi.repository.usuarios.UsuarioRepository;
import com.descansobygi.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthServiceImpl implements IAuthService {
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Transactional(readOnly = true)
    @Override
    public LoginResponseDTO login(LoginRequestDTO dto) {
        // 1. Buscar el usuario activo por mail (los eliminados no pueden loguearse).
        Usuario usuario = usuarioRepository.findByMailActivo(dto.mail())
                .orElseThrow(InvalidCredentialsException::new);

        // 2. Comparar la contraseña recibida con el hash BCrypt guardado.
        if (!passwordEncoder.matches(dto.contrasena(), usuario.getContrasena())) {
            throw new InvalidCredentialsException();
        }

        // 3. Generar el token y devolverlo junto con los datos del usuario.
        String token = jwtTokenProvider.generarToken(usuario);
        return LoginResponseDTO.of(token, jwtTokenProvider.getExpiracionSegundos(), usuario);
    }
}
