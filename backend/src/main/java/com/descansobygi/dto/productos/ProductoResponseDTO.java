package com.descansobygi.dto.productos;
import com.descansobygi.model.Producto;

import java.math.BigDecimal;

// devuelve los datos de un producto al cliente

public record ProductoResponseDTO(
        Long id,
        String nombre,
        String descripcion,
        BigDecimal precio,
        Integer stock,
        Long marcaId,
        String marcaNombre,
        Long categoriaId,
        String categoriaNombre
) {
    public static ProductoResponseDTO fromEntidad(Producto producto) {
        return new ProductoResponseDTO(
                producto.getId(),
                producto.getNombre(),
                producto.getDescripcion(),
                producto.getPrecio(),
                producto.getStock(),
                producto.getMarca().getId(),
                producto.getMarca().getNombre(),
                producto.getCategoria().getId(),
                producto.getCategoria().getNombre()
        );
    }
}
