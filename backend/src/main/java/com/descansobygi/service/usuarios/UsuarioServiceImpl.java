package com.descansobygi.service.usuarios;

import com.descansobygi.dto.usuarios.UsuarioEditDTO;
import com.descansobygi.dto.usuarios.UsuarioRequestDTO;
import com.descansobygi.dto.usuarios.UsuarioResponseDTO;
import com.descansobygi.enums.EstadoCarrito;
import com.descansobygi.exception.ResourceNotFoundException;
import com.descansobygi.model.Carrito;
import com.descansobygi.model.Usuario;
import com.descansobygi.repository.carrito.CarritoRepository;
import com.descansobygi.repository.usuarios.UsuarioRepository;
import com.descansobygi.service.carrito.ICarritoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class UsuarioServiceImpl implements IUsuarioService{
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private CarritoRepository carritoRepository;

    @Transactional
    @Override
    public UsuarioResponseDTO registrar(UsuarioRequestDTO usuarioRequestDTO){
        // Verificamos si el mail existe
        validarEmailNoExista(usuarioRequestDTO.mail());

        // Convertimos DTO a entidad
        Usuario usuario = usuarioRequestDTO.toEntidad();

        // Guradamos en la base de datos
        Usuario usuarioGuardado  = usuarioRepository.save(usuario);

        // Crear carrito automático
        // Después en registrar()
        Carrito carrito = new Carrito();
        carrito.setUsuario(usuarioGuardado);
        carrito.setEstado(EstadoCarrito.ACTIVO);
        carritoRepository.save(carrito);

        return  UsuarioResponseDTO.fromEntidad(usuarioGuardado);
    }


    @Transactional(readOnly = true)
    @Override
    public UsuarioResponseDTO obtenerPorId(Long id) {
        // Buscar usuario
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
        // Retornar DTO
        return UsuarioResponseDTO.fromEntidad(usuario);
    }
    @Transactional
    @Override
    public UsuarioResponseDTO modificar(Long id, UsuarioEditDTO dto) {
        // Buscar usuario
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        // Validar email solo si viene y cambió
        if (dto.mail() != null && !dto.mail().equals(usuario.getMail())) {
            validarEmailNoExista(dto.mail());
            usuario.setMail(dto.mail());
        }

        if (dto.nombre() != null)   usuario.setNombre(dto.nombre());
        if (dto.apellido() != null) usuario.setApellido(dto.apellido());
        if (dto.celular() != null)  usuario.setCelular(dto.celular());

        Usuario usuarioActualizado = usuarioRepository.save(usuario);
        return UsuarioResponseDTO.fromEntidad(usuarioActualizado);
    }


    @Transactional(readOnly = true)
    @Override
    public UsuarioResponseDTO buscarPorMail(String mail) {
        Usuario usuario = usuarioRepository.findByMailActivo(mail)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        return UsuarioResponseDTO.fromEntidad(usuario);
    }

    @Transactional
    @Override
    public UsuarioResponseDTO eliminarUsuario(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

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

