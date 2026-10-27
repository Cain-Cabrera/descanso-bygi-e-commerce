package com.descansobygi.controllers.pedidos;

import com.descansobygi.dto.pedido.PedidoRequestDTO;
import com.descansobygi.dto.pedido.PedidoResponseDTO;
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

    @PostMapping("/{usuarioId}")
    @Operation(summary = "Crear pedido", description = "Genera un pedido a partir del carrito activo del usuario")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Pedido creado"),
            @ApiResponse(responseCode = "400", description = "Carrito vacío o stock insuficiente"),
            @ApiResponse(responseCode = "404", description = "Usuario o carrito no encontrado")
    })
    public ResponseEntity<PedidoResponseDTO> crearPedido(@PathVariable("usuarioId") Long usuarioId,
                                                         @Valid @RequestBody PedidoRequestDTO dto) {
        PedidoResponseDTO respuesta = pedidoService.crearPedido(usuarioId, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
    }

    @GetMapping("/usuario/{usuarioId}")
    @Operation(summary = "Pedidos por usuario", description = "Lista los pedidos de un usuario")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pedidos encontrados"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public ResponseEntity<List<PedidoResponseDTO>> obtenerPedidosUsuario(@PathVariable("usuarioId") Long usuarioId) {
        return ResponseEntity.ok(pedidoService.obtenerPedidosUsuario(usuarioId));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener pedido", description = "Obtiene un pedido por su ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pedido encontrado"),
            @ApiResponse(responseCode = "404", description = "Pedido no encontrado")
    })
    public ResponseEntity<PedidoResponseDTO> obtenerPedido(@PathVariable("id") Long id) {
        return ResponseEntity.ok(pedidoService.obtenerPorId(id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Cancelar pedido", description = "Cancela el pedido y devuelve el stock")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Pedido cancelado"),
            @ApiResponse(responseCode = "404", description = "Pedido no encontrado")
    })
    public ResponseEntity<Void> cancelarPedido(@PathVariable("id") Long id) {
        pedidoService.cancelarPedido(id);
        return ResponseEntity.noContent().build();
    }

    // Lista todos los pedidos registrados.
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