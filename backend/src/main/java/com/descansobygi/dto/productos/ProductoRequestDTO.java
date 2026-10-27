package com.descansobygi.dto.productos;
import com.descansobygi.model.Categoria;
import com.descansobygi.model.Imagen;
import com.descansobygi.model.Marca;
import com.descansobygi.model.Producto;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.List;

// recibe lso datos necesarios para crear o modificar un producto

public record ProductoRequestDTO(

        // nombre obligatorio, hasta 100 caracteres
        @NotBlank(message = "El nombre es obligatorio")
        @Size(max = 100, message = "El nombre no puede superar los 100 caracteres")
        String nombre,

        // descripción opcional, hasta 255 caracteres
        @Size(max = 255, message = "La descripción no puede superar los 255 caracteres")
        String descripcion,

        // precio obligatorio y no puede ser negativo
        @NotNull(message = "El precio es obligatorio")
        @DecimalMin(value = "0.0", message = "El precio no puede ser negativo")
        BigDecimal precio,

        // stock obligatorio y no puede ser negativo (0 es válido: producto sin stock)
        @NotNull(message = "El stock es obligatorio")
        @PositiveOrZero(message = "El stock no puede ser negativo")
        Integer stock,

        // ids de las relaciones
        @NotNull(message = "La marca es obligatoria")
        Long marcaId,

        @NotNull(message = "La categoría es obligatoria")
        Long categoriaId,

        // URLs de las imágenes, opcional
        @Size(max = 10, message = "Un producto no puede tener más de 10 imágenes")
        List<@NotBlank(message = "La URL de la imagen no puede estar vacía") String> imagenes
) {

        public Producto toEntidad(Marca marca, Categoria categoria) {
                Producto producto = new Producto();
                producto.setNombre(this.nombre());
                producto.setDescripcion(this.descripcion());
                producto.setPrecio(this.precio());
                producto.setStock(this.stock());
                producto.setMarca(marca);
                producto.setCategoria(categoria);

                // Si el cliente mandó imágenes, creamos una entidad Imagen por cada URL
                if (this.imagenes() != null) {
                        for (String url : this.imagenes()) {
                                Imagen imagen = new Imagen();
                                imagen.setUrl(url);
                                imagen.setProducto(producto);
                                producto.getImagenes().add(imagen);
                        }
                }

                return producto;
        }
}
