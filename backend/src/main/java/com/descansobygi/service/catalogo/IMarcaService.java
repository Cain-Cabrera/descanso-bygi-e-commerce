package com.descansobygi.service.catalogo;

import com.descansobygi.dto.marca.MarcaRequestDTO;
import com.descansobygi.dto.marca.MarcaResponseDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IMarcaService {
    @Transactional
    MarcaResponseDTO crear(MarcaRequestDTO dto);

    @Transactional(readOnly = true)
    MarcaResponseDTO obtenerPorId(Long id);

    @Transactional(readOnly = true)
    List<MarcaResponseDTO> listar();

    @Transactional
    MarcaResponseDTO modificar(Long id, MarcaRequestDTO dto);

    @Transactional
    MarcaResponseDTO eliminar(Long id);
}
