package com.descansobygi.controllers;

import com.descansobygi.dto.usuarios.UsuarioRequestDTO;
import com.descansobygi.dto.usuarios.UsuarioResponseDTO;
import com.descansobygi.service.usuarios.IUsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/usuarios")
@Tag(name = "Usuarios", description = "Gestión de usuarios")
public class UsuarioController {
    @Autowired
    private IUsuarioService usuarioService;

    @PostMapping("/registrar")
    @Operation(summary = "Registrar usuario", description = "Crea un nuevo usuario")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Usuario creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Email ya existe")
    })
    public ResponseEntity<UsuarioResponseDTO> registrar(@RequestBody UsuarioRequestDTO dto) {
        UsuarioResponseDTO respuesta = usuarioService.registrar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
    }

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

    @PutMapping("/{id}")
    @Operation(summary = "Modificar usuario", description = "Actualiza los datos de un usuario")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario actualizado"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public ResponseEntity<UsuarioResponseDTO> modificar(@PathVariable Long id, @RequestBody UsuarioRequestDTO dto) {
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
