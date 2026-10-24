
package com.descansobygi.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// Representa un producto del catálogo de Descanso by Gi.
@Entity
@Table(name = "productos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Producto extends Base {

    // Nombre del producto; es obligatorio.
    @Column(nullable = false, length = 150)
    private String nombre;

    // Descripción opcional del producto.
    @Column(length = 1000)
    private String descripcion;

    // Precio del producto con dos decimales.
    @Column(nullable = false, precision = 10, scale = 2)
    private java.math.BigDecimal precio;

    // Cantidad disponible en stock.
    @Column(nullable = false)
    private Integer stock;

    // Muchos productos pueden pertenecer a una misma marca.
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "marca_id", nullable = false)
    private Marca marca;

    // Muchos productos pueden pertenecer a una misma categoría.
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;
}
