package com.descansobygi.service.catalogo;

import com.descansobygi.dto.categorias.CategoriaEditDTO;
import com.descansobygi.dto.categorias.CategoriaRequestDTO;
import com.descansobygi.dto.categorias.CategoriaResponseDTO;
import com.descansobygi.exception.BadRequestException;
import com.descansobygi.exception.ResourceNotFoundException;
import com.descansobygi.model.Categoria;
import com.descansobygi.repository.catalogo.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class CategoriaServiceImpl implements ICategoriaService{
    @Autowired
    private CategoriaRepository categoriaRepository;

    @Transactional
    @Override
    public CategoriaResponseDTO crear(CategoriaRequestDTO dto) {

        // Validar que no exista
        if (categoriaRepository.existsByNombre(dto.nombre())) {
            throw new RuntimeException("La categoría ya existe");
        }

        Categoria categoria = dto.toEntidad();
        Categoria categoriaGuardada = categoriaRepository.save(categoria);
        return CategoriaResponseDTO.fromEntidad(categoriaGuardada);
    }

    @Transactional(readOnly = true)
    @Override
    public CategoriaResponseDTO obtenerPorId(Long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
        return CategoriaResponseDTO.fromEntidad(categoria);
    }

    @Transactional(readOnly = true)
    public List<CategoriaResponseDTO> listar() {
        return categoriaRepository.findAll().stream()
                .filter(c -> !c.isEliminado())
                .map(CategoriaResponseDTO::fromEntidad)
                .collect(Collectors.toList());
    }
    @Transactional
    @Override
    public CategoriaResponseDTO modificar(Long id, CategoriaEditDTO dto) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada"));

        if (dto.nombre() != null && !dto.nombre().equals(categoria.getNombre())
                && categoriaRepository.existsByNombre(dto.nombre())) {
            throw new BadRequestException("Ya existe una categoría con ese nombre");
        }

        dto.aplicarA(categoria);
        return CategoriaResponseDTO.fromEntidad(categoriaRepository.save(categoria));
    }

    @Transactional
    @Override
    public CategoriaResponseDTO eliminar(Long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        if (!categoria.getProductos().isEmpty()) {
            throw new RuntimeException("No se puede eliminar categoría con productos");
        }

        categoria.setEliminado(true);
        return CategoriaResponseDTO.fromEntidad(categoriaRepository.save(categoria));
    }
}
