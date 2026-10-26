package com.descansobygi.service.usuarios;

import com.descansobygi.dto.usuarios.UsuarioRequestDTO;
import com.descansobygi.dto.usuarios.UsuarioResponseDTO;
import org.springframework.transaction.annotation.Transactional;

public interface IUsuarioService {
    @Transactional
    UsuarioResponseDTO registrar(UsuarioRequestDTO usuarioRequestDTO);

    @Transactional(readOnly = true)
    UsuarioResponseDTO obtenerPorId(Long id);

    @Transactional
    UsuarioResponseDTO modificar(Long id, UsuarioRequestDTO dto);

    @Transactional(readOnly = true)
    UsuarioResponseDTO buscarPorMail(String mail);

    @Transactional
    UsuarioResponseDTO eliminarUsuario(Long id);
}
