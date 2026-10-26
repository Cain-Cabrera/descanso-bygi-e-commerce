
package com.descansobygi.model;

import com.descansobygi.enums.FormaDePago;
import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

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

    @OneToMany(mappedBy = "pedido", fetch = FetchType.LAZY)
    private Set<DetallePedido> detalles = new HashSet<>();
}
