
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

import java.math.BigDecimal;

// Representa un producto agregado al carrito de compras.
@Entity
@Table(name = "items_carrito")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemCarrito extends Base {

    // Cantidad de unidades del producto agregadas al carrito.
    @Column(nullable = false)
    private Integer cantidad;

    // Subtotal correspondiente a este ítem.
    // El servicio deberá calcularlo y validarlo.
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal subtotal;

    // Muchos ítems pueden pertenecer al mismo carrito.
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "carrito_id", nullable = false)
    private Carrito carrito;

    // Un producto puede aparecer en distintos ítems de carritos.
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;
}
