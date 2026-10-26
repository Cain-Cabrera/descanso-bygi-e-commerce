package com.descansobygi.service.catalogo;

import com.descansobygi.dto.categorias.CategoriaRequestDTO;
import com.descansobygi.dto.categorias.CategoriaResponseDTO;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

public interface ICategoriaService {
    @Transactional
    CategoriaResponseDTO crear(CategoriaRequestDTO dto);

    @Transactional(readOnly = true)
    CategoriaResponseDTO obtenerPorId(Long id);

    @Transactional(readOnly = true)
    List<CategoriaResponseDTO> listar();

    @Transactional
    CategoriaResponseDTO modificar(Long id, CategoriaRequestDTO dto);

    @Transactional
    CategoriaResponseDTO eliminar(Long id);
}
