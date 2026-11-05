package com.descansobygi.controllers.pedidos;

import com.descansobygi.dto.pedido.PedidoRequestDTO;
import com.descansobygi.dto.pedido.PedidoResponseDTO;
import com.descansobygi.security.UsuarioActual;
import com.descansobygi.service.pedidos.IPedidoService;
import io.swagger.v3.oas.annotations.Operation;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
@Tag(name = "Pedidos", description = "Gestión de pedidos  cada usuario")
public class PedidoController {
    @Autowired
    private IPedidoService pedidoService;

    // El usuario sale del token: cada uno crea pedidos solo con su propio carrito.
    @PostMapping
    @Operation(summary = "Crear pedido", description = "Genera un pedido a partir del carrito activo del usuario")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Pedido creado"),
            @ApiResponse(responseCode = "400", description = "Carrito vacío o stock insuficiente"),
            @ApiResponse(responseCode = "404", description = "Usuario o carrito no encontrado")
    })
    public ResponseEntity<PedidoResponseDTO> crearPedido(@Valid @RequestBody PedidoRequestDTO dto) {
        PedidoResponseDTO respuesta = pedidoService.crearPedido(UsuarioActual.id(), dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
    }

    @GetMapping("/mis-pedidos")
    @Operation(summary = "Mis pedidos", description = "Lista los pedidos del usuario logueado")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pedidos encontrados")
    })
    public ResponseEntity<List<PedidoResponseDTO>> misPedidos() {
        return ResponseEntity.ok(pedidoService.obtenerPedidosUsuario(UsuarioActual.id()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener pedido", description = "Obtiene un pedido propio por su ID (el administrador puede ver cualquiera)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pedido encontrado"),
            @ApiResponse(responseCode = "404", description = "Pedido no encontrado")
    })
    public ResponseEntity<PedidoResponseDTO> obtenerPedido(@PathVariable("id") Long id) {
        return ResponseEntity.ok(pedidoService.obtenerPorId(id, UsuarioActual.id(), UsuarioActual.esAdmin()));
    }

    @DeleteMapping("/{id}")
    // Solo ADMINISTRADOR (ver SecurityConfig).
    @Operation(summary = "Cancelar pedido", description = "Cancela el pedido y devuelve el stock (solo administrador)")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Pedido cancelado"),
            @ApiResponse(responseCode = "400", description = "El pedido ya fue cancelado"),
            @ApiResponse(responseCode = "403", description = "Sin permisos"),
            @ApiResponse(responseCode = "404", description = "Pedido no encontrado")
    })
    public ResponseEntity<Void> cancelarPedido(@PathVariable("id") Long id) {
        pedidoService.cancelarPedido(id);
        return ResponseEntity.noContent().build();
    }

    // Lista todos los pedidos registrados. Solo ADMINISTRADOR (ver SecurityConfig).
    @GetMapping
    @Operation(
            summary = "Listar todos los pedidos",
            description = "Obtiene todos los pedidos registrados"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Pedidos encontrados"
    )
    public ResponseEntity<List<PedidoResponseDTO>> listarTodos() {
        return ResponseEntity.ok(pedidoService.listarTodos());
    }

}