package com.descansobygi.service.catalogo;

import com.descansobygi.dto.productos.ProductoEditDTO;
import com.descansobygi.dto.productos.ProductoRequestDTO;
import com.descansobygi.dto.productos.ProductoResponseDTO;
import com.descansobygi.exception.ResourceNotFoundException;
import com.descansobygi.model.Categoria;
import com.descansobygi.model.Imagen;
import com.descansobygi.model.Marca;
import com.descansobygi.model.Producto;
import com.descansobygi.repository.catalogo.CategoriaRepository;
import com.descansobygi.repository.catalogo.MarcaRepository;
import com.descansobygi.repository.catalogo.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
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
    public ProductoResponseDTO modificar(Long id, ProductoEditDTO dto) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado"));

        if (dto.nombre() != null)      producto.setNombre(dto.nombre());
        if (dto.descripcion() != null) producto.setDescripcion(dto.descripcion());
        if (dto.precio() != null)      producto.setPrecio(dto.precio());
        if (dto.stock() != null)       producto.setStock(dto.stock());

        if (dto.marcaId() != null) {
            producto.setMarca(obtenerMarcaOThrow(dto.marcaId()));
        }
        if (dto.categoriaId() != null) {
            producto.setCategoria(obtenerCategoriaOThrow(dto.categoriaId()));
        }

        // Si llegó la lista de imágenes, reemplaza las anteriores
        if (dto.imagenes() != null) {
            producto.getImagenes().clear();
            for (String url : dto.imagenes()) {
                Imagen imagen = new Imagen();
                imagen.setUrl(url);
                imagen.setProducto(producto);
                producto.getImagenes().add(imagen);
            }
        }

        return ProductoResponseDTO.fromEntidad(productoRepository.save(producto));
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
