package com.descansobygi.controllers.usuarios;

import com.descansobygi.dto.usuarios.UsuarioEditDTO;
import com.descansobygi.dto.usuarios.UsuarioRequestDTO;
import com.descansobygi.dto.usuarios.UsuarioResponseDTO;
import com.descansobygi.security.UsuarioActual;
import com.descansobygi.service.usuarios.IUsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/usuarios")
@Tag(name = "Usuarios", description = "Gestión de usuarios")
public class UsuarioController {
    @Autowired
    IUsuarioService usuarioService;


    // Crear un usuario
    @PostMapping("/registrar")
    // Endpoint público: en Swagger no muestra el candado.
    @SecurityRequirements
    @Operation(summary = "Registrar usuario", description = "Crea un nuevo usuario")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Usuario creado"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o email ya registrado")
    })
    public ResponseEntity<UsuarioResponseDTO> registrarUsuario(@Valid  @RequestBody UsuarioRequestDTO usuarioRequestDTO) {
        UsuarioResponseDTO respuesta = usuarioService.registrar(usuarioRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
    }


    // --- Perfil propio: el id sale del token, no de la URL ---

    @GetMapping("/me")
    @Operation(summary = "Mi perfil", description = "Obtiene los datos del usuario logueado")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Perfil encontrado"),
            @ApiResponse(responseCode = "401", description = "No autenticado")
    })
    public ResponseEntity<UsuarioResponseDTO> miPerfil() {
        return ResponseEntity.ok(usuarioService.obtenerPorId(UsuarioActual.id()));
    }

    @PatchMapping("/me")
    @Operation(summary = "Modificar mi perfil", description = "Actualiza los datos del usuario logueado (no permite cambiar el rol)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Perfil actualizado"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o email ya registrado"),
            @ApiResponse(responseCode = "401", description = "No autenticado")
    })
    public ResponseEntity<UsuarioResponseDTO> modificarMiPerfil(@Valid @RequestBody UsuarioEditDTO dto) {
        return ResponseEntity.ok(usuarioService.modificar(UsuarioActual.id(), dto));
    }

    // --- Gestión de usuarios: solo ADMINISTRADOR (ver SecurityConfig) ---

    @GetMapping("/{id}")
    @Operation(summary = "Obtener usuario", description = "Obtiene un usuario por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public ResponseEntity<UsuarioResponseDTO> obtenerPorId(@PathVariable Long id) {
        UsuarioResponseDTO respuesta = usuarioService.obtenerPorId(id);
        return ResponseEntity.ok(respuesta);
    }

    @GetMapping("/buscar")
    @Operation(summary = "Buscar usuario por mail", description = "Obtiene un usuario por su correo electrónico")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public ResponseEntity<UsuarioResponseDTO> buscarPorMail(@RequestParam("mail") String mail) {
        return ResponseEntity.ok(usuarioService.buscarPorMail(mail));
    }

    @GetMapping
    @Operation(summary = "Listar usuarios", description = "Devuelve los usuarios activos")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuarios encontrados")
    })
    public ResponseEntity<List<UsuarioResponseDTO>> listarUsuarios() {
        return ResponseEntity.ok(usuarioService.listar());
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Modificar usuario", description = "Actualiza los datos de un usuario")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario actualizado"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o email ya registrado"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public ResponseEntity<UsuarioResponseDTO> modificar(@PathVariable Long id,@Valid @RequestBody UsuarioEditDTO dto) {
        UsuarioResponseDTO respuesta = usuarioService.modificar(id, dto);
        return ResponseEntity.ok(respuesta);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar usuario", description = "Marca un usuario como eliminado")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario eliminado"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public ResponseEntity<UsuarioResponseDTO> eliminarUsuario(@PathVariable Long id) {
        UsuarioResponseDTO respuesta = usuarioService.eliminarUsuario(id);
        return ResponseEntity.ok(respuesta);
    }


}
