package com.descansobygi.dto.productos;
import com.descansobygi.model.Producto;

import java.math.BigDecimal;
import java.util.List;

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
        String categoriaNombre,
        List<String> imagenes
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
                producto.getCategoria().getNombre(),
                producto.getImagenes().stream()
                        .filter(imagen -> !imagen.isEliminado())
                        .map(imagen -> imagen.getUrl())
                        .toList()
        );
    }
}
