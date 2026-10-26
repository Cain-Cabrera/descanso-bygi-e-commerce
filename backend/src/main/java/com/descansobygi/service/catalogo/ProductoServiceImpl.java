package com.descansobygi.service.catalogo;

import com.descansobygi.dto.productos.ProductoRequestDTO;
import com.descansobygi.dto.productos.ProductoResponseDTO;
import com.descansobygi.model.Categoria;
import com.descansobygi.model.Marca;
import com.descansobygi.model.Producto;
import com.descansobygi.repository.catalogo.CategoriaRepository;
import com.descansobygi.repository.catalogo.MarcaRepository;
import com.descansobygi.repository.catalogo.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

public class ProductoServiceImpl implements IProductoService{

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private MarcaRepository marcaRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Transactional
    @Override
    public ProductoResponseDTO crear(ProductoRequestDTO dto) {
        // Buscar marca
        Marca marca = obtenerMarcaOThrow(dto.marcaId());

        // Buscar categoría
        Categoria categoria = obtenerCategoriaOThrow(dto.categoriaId());

        // Convertir DTO a entidad
        Producto producto = dto.toEntidad(marca, categoria);

        // Guardar
        Producto productoGuardado = productoRepository.save(producto);

        // Retornar DTO
        return ProductoResponseDTO.fromEntidad(productoGuardado);
    }

    @Transactional(readOnly = true)
    @Override
    public ProductoResponseDTO obtenerPorId(Long id) {
        Producto producto = productoRepository.findById(id).
        orElseThrow(()-> new RuntimeException("Producto no encontrado"));
        return ProductoResponseDTO.fromEntidad(producto);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ProductoResponseDTO> listar() {
        List<Producto> productos = productoRepository.findAll();

        return productos.stream()
                .filter(p -> !p.isEliminado())
                .map(ProductoResponseDTO::fromEntidad)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public List<ProductoResponseDTO> buscarPorNombre(String nombre) {
        List<Producto> productos = productoRepository.findByNombreActivo(nombre);
        return productos.stream()
                .map(ProductoResponseDTO::fromEntidad)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public List<ProductoResponseDTO> filtrarPorCategoria(Long categoriaId) {
        List<Producto> productos = productoRepository.findByCategoriaIdActivo(categoriaId);

        return productos.stream()
                .map(ProductoResponseDTO::fromEntidad)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public List<ProductoResponseDTO> filtrarPorMarca(Long marcaId) {
        List<Producto> productos = productoRepository.findByMarcaIdAndEliminadoFalse(marcaId);

        return productos.stream()
                .map(ProductoResponseDTO::fromEntidad)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public ProductoResponseDTO modificar(Long id, ProductoRequestDTO dto) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        Marca marca = obtenerMarcaOThrow(dto.marcaId());
        Categoria categoria = obtenerCategoriaOThrow(dto.categoriaId());

        producto.setNombre(dto.nombre());
        producto.setDescripcion(dto.descripcion());
        producto.setPrecio(dto.precio());
        producto.setStock(dto.stock());
        producto.setMarca(marca);
        producto.setCategoria(categoria);

        Producto productoActualizado = productoRepository.save(producto);

        return ProductoResponseDTO.fromEntidad(productoActualizado);
    }

    @Transactional
    @Override
    public ProductoResponseDTO eliminar(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        producto.setEliminado(true);
        Producto productoEliminado = productoRepository.save(producto);

        return ProductoResponseDTO.fromEntidad(productoEliminado);
    }

    /*
    * Metodos propios y privado
    */
    private Marca obtenerMarcaOThrow(Long marcaId) {
        return marcaRepository.findById(marcaId)
                .orElseThrow(() -> new RuntimeException("Marca no encontrada"));
    }
    private Categoria obtenerCategoriaOThrow(Long categoriaId) {
        return categoriaRepository.findById(categoriaId)
                .orElseThrow(() -> new RuntimeException("Categoria no encontrada"));
    }
}
