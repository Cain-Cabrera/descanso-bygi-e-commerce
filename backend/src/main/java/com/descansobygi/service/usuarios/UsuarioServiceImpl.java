package com.descansobygi.service.usuarios;

import com.descansobygi.dto.usuarios.UsuarioRequestDTO;
import com.descansobygi.dto.usuarios.UsuarioResponseDTO;
import com.descansobygi.model.Usuario;
import com.descansobygi.repository.usuarios.UsuarioRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

public class UsuarioServiceImpl implements IUsuarioService{
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional
    @Override
    public UsuarioResponseDTO registrar(UsuarioRequestDTO usuarioRequestDTO){
        // Verificamos si el mail existe
        validarEmailNoExista(usuarioRequestDTO.mail());

        // Convertimos DTO a entidad
        Usuario usuario = usuarioRequestDTO.toEntidad();

        // Guradamos en la base de datos
        Usuario usuarioGuardado  = usuarioRepository.save(usuario);
        return  UsuarioResponseDTO.fromEntidad(usuarioGuardado);
    }


    @Transactional(readOnly = true)
    @Override
    public UsuarioResponseDTO obtenerPorId(Long id) {
        // Buscar usuario
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        // Retornar DTO
        return UsuarioResponseDTO.fromEntidad(usuario);
    }
    @Transactional
    @Override
    public UsuarioResponseDTO modificar(Long id, UsuarioRequestDTO dto) {
        // Buscar usuario
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Validar email si cambió
        if (!usuario.getMail().equals(dto.mail())) {
            validarEmailNoExista(dto.mail());
        }

        // Actualizar
        usuario.setNombre(dto.nombre());
        usuario.setApellido(dto.apellido());
        usuario.setMail(dto.mail());
        usuario.setCelular(dto.celular());

        // Guardar
        Usuario usuarioActualizado = usuarioRepository.save(usuario);

        // Retornar DTO
        return UsuarioResponseDTO.fromEntidad(usuarioActualizado);
    }


    @Transactional(readOnly = true)
    @Override
    public UsuarioResponseDTO buscarPorMail(String mail) {
        Usuario usuario = usuarioRepository.findByMailActivo(mail)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        return UsuarioResponseDTO.fromEntidad(usuario);
    }

    @Transactional
    @Override
    public UsuarioResponseDTO eliminarUsuario(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Marcar como eliminado (borrado lógico)
        usuario.setEliminado(true);

        // Guardar
        Usuario usuarioEliminado = usuarioRepository.save(usuario);

        return UsuarioResponseDTO.fromEntidad(usuarioEliminado);
    }


    /**
     * Metodos propios y privado
     */
    private void validarEmailNoExista(String mail) {
        if (usuarioRepository.existsByMail(mail)) {
            throw new IllegalArgumentException("El correo ya está registrado");
        }
    }

}

