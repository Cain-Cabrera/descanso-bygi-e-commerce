
package com.descansobygi.model;

import jakarta.persistence.*;
import org.hibernate.annotations.Check;

import lombok.*;

import java.util.HashSet;
import java.util.Set;

// Representa un producto del catálogo de Descanso by Gi.
// Conserva igualdad por identidad de objeto para no depender de campos mutables.
@Entity
@Table(name = "productos")
@Check(constraints = "precio >= 0 AND stock >= 0")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Producto extends Base {

    // Nombre del producto; es obligatorio.
    @Column(nullable = false, length = 150)
    private String nombre;

    // Descripción opcional del producto.
    @Column(length = 500)
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

    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Imagen> imagenes = new HashSet<>();

    @OneToMany(mappedBy = "producto", fetch = FetchType.LAZY)
    private Set<ItemCarrito> itemsCarrito = new HashSet<>();

    @OneToMany(mappedBy = "producto", fetch = FetchType.LAZY)
    private Set<DetallePedido> detalles = new HashSet<>();
}
