package com.descansobygi.controllers.catalogo;

import com.descansobygi.dto.categorias.CategoriaEditDTO;
import com.descansobygi.dto.categorias.CategoriaRequestDTO;
import com.descansobygi.dto.categorias.CategoriaResponseDTO;
import com.descansobygi.service.catalogo.ICategoriaService;
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
@RequestMapping("/api/catalogo/categoria")
@Tag(name = "categoria", description = "Gestión de las categorias")
public class CategoriaController {

    @Autowired
    private ICategoriaService categoriaService;

    // Crear cattgoria
    @PostMapping("/Registrar")
    @Operation(summary = "Crear categoria", description = "Agregar una nueva categoria")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Categoria creada"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    public ResponseEntity<CategoriaResponseDTO> createCategoria(@Valid @RequestBody CategoriaRequestDTO categoriaRequestDTO) {
        CategoriaResponseDTO respuesta = categoriaService.crear(categoriaRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener categoria", description = "Obtiene una categoria por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Categoria encontrada"),
            @ApiResponse(responseCode = "404", description = "Categoria no encontrada")
    })
    public ResponseEntity<CategoriaResponseDTO> buscarCategoriaPorId(@PathVariable Long id) {
        CategoriaResponseDTO respuesta = categoriaService.obtenerPorId(id);
        return ResponseEntity.status(HttpStatus.OK).body(respuesta);
    }

    @GetMapping
    @Operation(summary = "Obtener categorias", description = "Lista de categoria")
    @ApiResponse(responseCode = "200",description = "Categorias encontradas")
    public ResponseEntity<List<CategoriaResponseDTO>> listarCategorias() {
        List<CategoriaResponseDTO> respuesta = categoriaService.listar();
        return ResponseEntity.status(HttpStatus.OK).body(respuesta);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Modicar categoria",description = "Modificar la categoria por su id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Categoría modificada"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "404", description = "Categoría no encontrada")
    })
    public ResponseEntity<CategoriaResponseDTO> modificarCategoria(@PathVariable Long id, @Valid @RequestBody CategoriaEditDTO categoriaEditDTO) {
        CategoriaResponseDTO respuesta = categoriaService.modificar(id, categoriaEditDTO);
        return ResponseEntity.ok(respuesta);
    }
}
