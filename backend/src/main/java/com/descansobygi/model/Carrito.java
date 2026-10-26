
package com.descansobygi.model;

import com.descansobygi.enums.EstadoCarrito;
import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

// Representa el carrito de compras de un usuario.
@Entity
@Table(name = "carritos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Carrito extends Base {

    // Estado funcional del carrito: ACTIVO o ELIMINADO.
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EstadoCarrito estado;

    // Un usuario puede tener varios carritos históricos.
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @OneToMany(mappedBy = "carrito", fetch = FetchType.LAZY)
    private Set<ItemCarrito> items = new HashSet<>();
}
