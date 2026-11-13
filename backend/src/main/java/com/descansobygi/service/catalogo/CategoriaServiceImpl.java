package com.descansobygi.service.catalogo;

import com.descansobygi.dto.categorias.CategoriaEditDTO;
import com.descansobygi.dto.categorias.CategoriaRequestDTO;
import com.descansobygi.dto.categorias.CategoriaResponseDTO;
import com.descansobygi.exception.BadRequestException;
import com.descansobygi.exception.ResourceNotFoundException;
import com.descansobygi.model.Categoria;
import com.descansobygi.repository.catalogo.CategoriaRepository;
import com.descansobygi.repository.catalogo.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class CategoriaServiceImpl implements ICategoriaService{
    @Autowired
    private CategoriaRepository categoriaRepository;
    @Autowired
    private ProductoRepository productoRepository;

    @Transactional
    @Override
    public CategoriaResponseDTO crear(CategoriaRequestDTO dto) {

        // Validar que no exista
        if (categoriaRepository.existsByNombreAndEliminadoFalse(dto.nombre())) {
            throw new BadRequestException("La categoría ya existe");
        }

        Categoria categoria = dto.toEntidad();
        Categoria categoriaGuardada = categoriaRepository.save(categoria);
        return CategoriaResponseDTO.fromEntidad(categoriaGuardada);
    }

    @Transactional(readOnly = true)
    @Override
    public CategoriaResponseDTO obtenerPorId(Long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .filter(c -> !c.isEliminado())
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada"));
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
                && categoriaRepository.existsByNombreAndEliminadoFalse(dto.nombre())) {
            throw new BadRequestException("Ya existe una categoría con ese nombre");
        }

        dto.aplicarA(categoria);
        return CategoriaResponseDTO.fromEntidad(categoriaRepository.save(categoria));
    }

    @Transactional
    @Override
    public CategoriaResponseDTO eliminar(Long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .filter(c -> !c.isEliminado())
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada"));

        if (productoRepository.countByCategoriaIdAndEliminadoFalse(id) > 0) {
            throw new BadRequestException("No se puede eliminar una categoría con productos activos");
        }

        categoria.setEliminado(true);
        return CategoriaResponseDTO.fromEntidad(categoriaRepository.save(categoria));
    }
}
