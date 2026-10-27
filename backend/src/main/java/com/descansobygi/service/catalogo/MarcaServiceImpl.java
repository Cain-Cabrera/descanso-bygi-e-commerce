package com.descansobygi.service.catalogo;

import com.descansobygi.dto.marca.MarcaEditDTO;
import com.descansobygi.dto.marca.MarcaRequestDTO;
import com.descansobygi.dto.marca.MarcaResponseDTO;
import com.descansobygi.exception.BadRequestException;
import com.descansobygi.exception.ResourceNotFoundException;
import com.descansobygi.model.Marca;
import com.descansobygi.repository.catalogo.MarcaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class MarcaServiceImpl implements IMarcaService {
    @Autowired
    private MarcaRepository marcaRepository;

    @Transactional
    @Override
    public MarcaResponseDTO crear(MarcaRequestDTO dto) {
        if (marcaRepository.existsByNombre(dto.nombre())) {
            throw new RuntimeException("La marca ya existe");
        }

        Marca marca = dto.toEntidad();
        Marca marcaGuardada = marcaRepository.save(marca);
        return MarcaResponseDTO.fromEntidad(marcaGuardada);
    }

    @Transactional(readOnly = true)
    @Override
    public MarcaResponseDTO obtenerPorId(Long id) {
        Marca marca = marcaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Marca no encontrada"));
        return MarcaResponseDTO.fromEntidad(marca);
    }

    @Transactional(readOnly = true)
    @Override
    public List<MarcaResponseDTO> listar() {
        return marcaRepository.findAll().stream()
                .filter(m -> !m.isEliminado())
                .map(MarcaResponseDTO::fromEntidad)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public MarcaResponseDTO modificar(Long id, MarcaEditDTO dto) {
        Marca marca = marcaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Marca no encontrada"));

        if (dto.nombre() != null && !dto.nombre().equals(marca.getNombre())
                && marcaRepository.existsByNombre(dto.nombre())) {
            throw new BadRequestException("Ya existe una marca con ese nombre");
        }

        if (dto.nombre() != null) {
            marca.setNombre(dto.nombre());
        }

        return MarcaResponseDTO.fromEntidad(marcaRepository.save(marca));
    }

    @Transactional
    @Override
    public MarcaResponseDTO eliminar(Long id) {
        Marca marca = marcaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Marca no encontrada"));

        if (!marca.getProductos().isEmpty()) {
            throw new RuntimeException("No se puede eliminar marca con productos");
        }

        marca.setEliminado(true);
        return MarcaResponseDTO.fromEntidad(marcaRepository.save(marca));
    }
}
