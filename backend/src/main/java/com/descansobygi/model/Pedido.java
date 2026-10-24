
package com.descansobygi.model;

import com.descansobygi.enums.FormaDePago;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

// Representa un pedido realizado por un usuario.
@Entity
@Table(name = "pedidos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Pedido extends Base {

    // Importe total del pedido.
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal total;

    // Forma de pago seleccionada para el pedido.
    @Enumerated(EnumType.STRING)
    @Column(name = "forma_de_pago", nullable = false, length = 20)
    private FormaDePago formaDePago;

    // Un usuario puede realizar múltiples pedidos.
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;
}
