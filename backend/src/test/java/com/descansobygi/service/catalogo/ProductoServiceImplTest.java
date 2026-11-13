package com.descansobygi.service.catalogo;

import com.descansobygi.dto.productos.ProductoEditDTO;
import com.descansobygi.dto.productos.ProductoResponseDTO;
import com.descansobygi.model.Categoria;
import com.descansobygi.model.Imagen;
import com.descansobygi.model.Marca;
import com.descansobygi.model.Producto;
import com.descansobygi.repository.catalogo.CategoriaRepository;
import com.descansobygi.repository.catalogo.MarcaRepository;
import com.descansobygi.repository.catalogo.ProductoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductoServiceImplTest {

    @Mock
    private ProductoRepository productoRepository;

    @Mock
    private MarcaRepository marcaRepository;

    @Mock
    private CategoriaRepository categoriaRepository;

    @InjectMocks
    private ProductoServiceImpl productoService;

    @Test
    void productosDistintosNoSeConsideranIgualesPorCompartirIdentificadorInicial() {
        assertNotEquals(crearProducto(), crearProducto());
    }

    @Test
    void modificarImagenesReemplazaLasAnteriores() {
        // El producto ya tiene dos imágenes anteriores asociadas.
        Producto producto = crearProducto();
        Imagen imagenAnterior = crearImagen(producto, "https://ejemplo.com/antes.jpg");
        Imagen otraImagenAnterior = crearImagen(producto, "https://ejemplo.com/otra.jpg");
        producto.getImagenes().add(imagenAnterior);
        producto.getImagenes().add(otraImagenAnterior);

        when(productoRepository.findById(1L)).thenReturn(Optional.of(producto));
        when(productoRepository.save(any(Producto.class))).thenAnswer(invocacion -> invocacion.getArgument(0));

        // Mandar una lista reemplaza físicamente las filas anteriores (orphanRemoval).
        ProductoEditDTO cambios = new ProductoEditDTO(null, null, null, null, null, null,
                List.of("https://ejemplo.com/nueva.jpg"));
        ProductoResponseDTO respuesta = productoService.modificar(1L, cambios);

        assertEquals(1, producto.getImagenes().size());
        assertEquals("https://ejemplo.com/nueva.jpg", producto.getImagenes().iterator().next().getUrl());
        assertFalse(producto.getImagenes().contains(imagenAnterior));
        assertFalse(producto.getImagenes().contains(otraImagenAnterior));
        assertEquals(List.of("https://ejemplo.com/nueva.jpg"), respuesta.imagenes());
    }

    private Producto crearProducto() {
        Marca marca = new Marca();
        marca.setId(1L);
        marca.setNombre("Marca de prueba");

        Categoria categoria = new Categoria();
        categoria.setId(1L);
        categoria.setNombre("Categoría de prueba");

        Producto producto = new Producto();
        producto.setId(1L);
        producto.setNombre("Producto de prueba");
        producto.setMarca(marca);
        producto.setCategoria(categoria);
        return producto;
    }

    private Imagen crearImagen(Producto producto, String url) {
        Imagen imagen = new Imagen();
        imagen.setProducto(producto);
        imagen.setUrl(url);
        return imagen;
    }
}
