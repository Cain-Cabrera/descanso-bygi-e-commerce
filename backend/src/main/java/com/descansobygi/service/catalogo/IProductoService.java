package com.descansobygi.service.catalogo;

import com.descansobygi.dto.productos.ProductoEditDTO;
import com.descansobygi.dto.productos.ProductoRequestDTO;
import com.descansobygi.dto.productos.ProductoResponseDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IProductoService {
    @Transactional
    ProductoResponseDTO crear(ProductoRequestDTO dto);

    @Transactional(readOnly = true)
    ProductoResponseDTO obtenerPorId(Long id);

    @Transactional(readOnly = true)
    List<ProductoResponseDTO> listar();

    @Transactional(readOnly = true)
    List<ProductoResponseDTO> buscarPorNombre(String nombre);

    @Transactional(readOnly = true)
    List<ProductoResponseDTO> filtrarPorCategoria(Long categoriaId);

    @Transactional(readOnly = true)
    List<ProductoResponseDTO> filtrarPorMarca(Long marcaId);

    @Transactional
    ProductoResponseDTO modificar(Long id, ProductoEditDTO dto);

    @Transactional
    ProductoResponseDTO eliminar(Long id);
}
