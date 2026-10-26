package com.descansobygi.dto.imagen;

import com.descansobygi.model.Imagen;
import com.descansobygi.model.Producto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

// recibe los datos para asociar una imagen a un producto

public record ImagenRequestDTO(

        // url obligatorio y hasta 500 caracteres
        @NotBlank(message = "La URL de la imagen es obligatoria")
        @Size(max = 500, message = "La url no puede sueprar los 500 caracteres")
        String url,

        //el producto al que pertenece la imagen es obligatorio
        @NotNull(message = "El id del producto es obligatorio")
        Long productoId
) {

        public Imagen toEntidad(Producto producto) {
                Imagen imagen = new Imagen();
                imagen.setUrl(this.url());
                imagen.setProducto(producto);
                return imagen;
        }
}
