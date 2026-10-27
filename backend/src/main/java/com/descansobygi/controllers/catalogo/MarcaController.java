package com.descansobygi.controllers.catalogo;

import com.descansobygi.dto.marca.MarcaEditDTO;
import com.descansobygi.dto.marca.MarcaRequestDTO;
import com.descansobygi.dto.marca.MarcaResponseDTO;
import com.descansobygi.service.catalogo.IMarcaService;
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
@RequestMapping("/api/catalogo/Marca")
@Tag(name = "categoria", description = "Gestión de las marcas")
public class MarcaController {
    @Autowired
    IMarcaService marcaService;


    @PostMapping("/crear")
    @Operation(summary = "Crear marca", description = "Agrega una nueva marca")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Marca creada"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    public ResponseEntity<MarcaResponseDTO> crearMarca(@Valid @RequestBody MarcaRequestDTO marcaRequestDTO) {
        MarcaResponseDTO respuesta = marcaService.crear(marcaRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener marca", description = "Obtiene una marca por su ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Marca encontrada"),
            @ApiResponse(responseCode = "404", description = "Marca no encontrada")
    })
    public ResponseEntity<MarcaResponseDTO> buscarMarcaPorId(@PathVariable("id") Long id) {
        MarcaResponseDTO respuesta = marcaService.obtenerPorId(id);
        return ResponseEntity.ok(respuesta);
    }

    @GetMapping
    @Operation(summary = "Listar marcas", description = "Devuelve la lista de marcas")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Marcas encontradas")
    })
    public ResponseEntity<List<MarcaResponseDTO>> listarMarcas() {
        return ResponseEntity.ok(marcaService.listar());
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Modificar marca", description = "Modifica parcialmente una marca por su ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Marca modificada"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "404", description = "Marca no encontrada")
    })
    public ResponseEntity<MarcaResponseDTO> modificarMarca(@PathVariable("id") Long id,
                                                           @Valid @RequestBody MarcaEditDTO marcaEditDTO) {
        MarcaResponseDTO respuesta = marcaService.modificar(id, marcaEditDTO);
        return ResponseEntity.ok(respuesta);
    }


}
