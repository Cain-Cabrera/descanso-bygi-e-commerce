
package com.descansobygi.model;

import jakarta.persistence.*;

import lombok.*;

import java.util.HashSet;
import java.util.Set;

// Representa un producto del catálogo de Descanso by Gi.
@Entity
@Table(name = "productos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id"},callSuper = false)
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

    @OneToMany(mappedBy = "producto", fetch = FetchType.LAZY)
    private Set<Imagen> imagenes = new HashSet<>();

    @OneToMany(mappedBy = "producto", fetch = FetchType.LAZY)
    private Set<ItemCarrito> itemsCarrito = new HashSet<>();

    @OneToMany(mappedBy = "producto", fetch = FetchType.LAZY)
    private Set<DetallePedido> detalles = new HashSet<>();
}
