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

        // El límite coincide con la columna nombre de la entidad Producto.
        @NotBlank(message = "El nombre es obligatorio")
        @Size(max = 150, message = "El nombre no puede superar los 150 caracteres")
        String nombre,

        // La descripción tiene un máximo de 500 caracteres, como en el diccionario de datos.
        @Size(max = 500, message = "La descripción no puede superar los 500 caracteres")
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

        // Un producto puede tener cero o varias imágenes; cada URL respeta el diccionario de datos.
        List<@NotBlank(message = "La URL de la imagen no puede estar vacía")
                @Size(max = 500, message = "La URL de la imagen no puede superar los 500 caracteres") String> imagenes
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
