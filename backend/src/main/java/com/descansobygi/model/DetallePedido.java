
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

// Representa un producto incluido en un pedido confirmado.
@Entity
@Table(name = "detalles_pedido")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DetallePedido extends Base {

    // Cantidad de unidades compradas.
    @Column(nullable = false)
    private Integer cantidad;

    // Precio unitario al momento de realizar la compra.
    @Column(name = "precio_unitario", nullable = false, precision = 10, scale = 2)
    private BigDecimal precioUnitario;

    // Subtotal de este detalle: precio unitario por cantidad.
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal subtotal;

    // Un pedido puede contener varios detalles.
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pedido_id", nullable = false)
    private Pedido pedido;

    // Un producto puede aparecer en distintos pedidos.
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;

    public void calcularSubtotal() {
        if (this.producto != null && this.cantidad != null) {
            this.subtotal = this.producto.getPrecio()
                    .multiply(new BigDecimal(this.cantidad));
        }
    }
}
