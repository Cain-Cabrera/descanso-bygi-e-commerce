package com.descansobygi.controllers.carrito;

import com.descansobygi.dto.carrito.CarritoResponseDTO;
import com.descansobygi.dto.itemCarrito.ItemCarritoRequestDTO;
import com.descansobygi.dto.itemCarrito.ItemCarritoResponseDTO;
import com.descansobygi.service.carrito.ICarritoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/carrito")
@Tag(name = "Carrito", description = "Gestión del carrito de compras de cada usuario")
public class CarritoController {
    @Autowired
    private ICarritoService carritoService;

    @GetMapping("/{usuarioId}")
    @Operation(summary = "Obtener carrito", description = "Obtiene el carrito activo del usuario")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Carrito encontrado"),
            @ApiResponse(responseCode = "404", description = "Usuario o carrito no encontrado")
    })
    public ResponseEntity<CarritoResponseDTO> obtenerCarrito(@PathVariable("usuarioId") Long usuarioId) {
        return ResponseEntity.ok(carritoService.obtenerCarritoActivo(usuarioId));
    }

    @PostMapping("/{usuarioId}/items")
    @Operation(summary = "Agregar item", description = "Agrega un producto al carrito activo del usuario")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Item agregado"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o stock insuficiente"),
            @ApiResponse(responseCode = "404", description = "Carrito o producto no encontrado")
    })
    public ResponseEntity<ItemCarritoResponseDTO> agregarItem(@PathVariable("usuarioId") Long usuarioId,
                                                              @Valid @RequestBody ItemCarritoRequestDTO dto) {
        ItemCarritoResponseDTO respuesta = carritoService.agregarAlCarrito(usuarioId, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
    }

    @PatchMapping("/{usuarioId}/items/{itemId}")
    @Operation(summary = "Modificar cantidad", description = "Cambia la cantidad de un item del carrito")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cantidad modificada"),
            @ApiResponse(responseCode = "400", description = "Cantidad inválida o stock insuficiente"),
            @ApiResponse(responseCode = "404", description = "Item no encontrado")
    })
    public ResponseEntity<ItemCarritoResponseDTO> modificarCantidad(@PathVariable("usuarioId") Long usuarioId,
                                                                    @PathVariable("itemId") Long itemId,
                                                                    @RequestParam Integer cantidad) {
        return ResponseEntity.ok(carritoService.modificarCantidad(usuarioId, itemId, cantidad));
    }

    @DeleteMapping("/{usuarioId}/items/{itemId}")
    @Operation(summary = "Eliminar item", description = "Quita un item del carrito")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Item eliminado"),
            @ApiResponse(responseCode = "404", description = "Item no encontrado")
    })
    public ResponseEntity<Void> eliminarItem(@PathVariable("usuarioId") Long usuarioId,
                                             @PathVariable("itemId") Long itemId) {
        carritoService.eliminarDelCarrito(usuarioId, itemId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{usuarioId}")
    @Operation(summary = "Vaciar carrito", description = "Quita todos los items del carrito activo")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Carrito vaciado"),
            @ApiResponse(responseCode = "404", description = "Carrito no encontrado")
    })
    public ResponseEntity<Void> vaciarCarrito(@PathVariable("usuarioId") Long usuarioId) {
        carritoService.vaciarCarrito(usuarioId);
        return ResponseEntity.noContent().build();
    }
}
