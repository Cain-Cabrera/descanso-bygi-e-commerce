package com.descansobygi.controllers.catalogo;

import com.descansobygi.dto.productos.ProductoEditDTO;
import com.descansobygi.dto.productos.ProductoRequestDTO;
import com.descansobygi.dto.productos.ProductoResponseDTO;
import com.descansobygi.service.catalogo.IProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
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
@RequestMapping("/api/catalogo/producto")
@Tag(name = "Producto", description = "Gestión de los productos")
public class ProductoController {
    @Autowired
    private IProductoService productoService;

    @PostMapping("/crear")
    @Operation(summary = "Crear producto", description = "Agrega un nuevo producto con sus imágenes")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Producto creado"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "404", description = "Marca o categoría no encontrada")
    })
    public ResponseEntity<ProductoResponseDTO> crearProducto(@Valid @RequestBody ProductoRequestDTO productoRequestDTO) {
        ProductoResponseDTO respuesta = productoService.crear(productoRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener producto", description = "Obtiene un producto por su ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Producto encontrado"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    public ResponseEntity<ProductoResponseDTO> obtenerProducto(@PathVariable("id") Long id) {
        return ResponseEntity.ok(productoService.obtenerPorId(id));
    }

    @GetMapping
    @Operation(summary = "Listar productos", description = "Devuelve la lista de productos")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Productos encontrados")
    })
    public ResponseEntity<List<ProductoResponseDTO>> listarProductos() {
        return ResponseEntity.ok(productoService.listar());
    }

    @GetMapping("/buscar")
    @Operation(summary = "Buscar productos", description = "Busca productos por nombre")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Productos encontrados")
    })
    public ResponseEntity<List<ProductoResponseDTO>> buscarPorNombre(@RequestParam String nombre) {
        return ResponseEntity.ok(productoService.buscarPorNombre(nombre));
    }

    @GetMapping("/categoria/{categoriaId}")
    @Operation(summary = "Productos por categoría", description = "Filtra productos por categoría")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Productos encontrados"),
            @ApiResponse(responseCode = "404", description = "Categoría no encontrada")
    })
    public ResponseEntity<List<ProductoResponseDTO>> filtrarPorCategoria(@PathVariable("categoriaId") Long categoriaId) {
        return ResponseEntity.ok(productoService.filtrarPorCategoria(categoriaId));
    }

    @GetMapping("/marca/{marcaId}")
    @Operation(summary = "Productos por marca", description = "Filtra productos por marca")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Productos encontrados"),
            @ApiResponse(responseCode = "404", description = "Marca no encontrada")
    })
    public ResponseEntity<List<ProductoResponseDTO>> filtrarPorMarca(@PathVariable("marcaId") Long marcaId) {
        return ResponseEntity.ok(productoService.filtrarPorMarca(marcaId));
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Modificar producto", description = "Modifica parcialmente un producto por su ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Producto modificado"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    public ResponseEntity<ProductoResponseDTO> modificarProducto(@PathVariable("id") Long id,
                                                                 @Valid @RequestBody ProductoEditDTO productoEditDTO) {
        return ResponseEntity.ok(productoService.modificar(id, productoEditDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar producto", description = "Marca un producto como eliminado (borrado lógico)")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Producto eliminado"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    public ResponseEntity<Void> eliminarProducto(@PathVariable("id") Long id) {
        productoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
