
package com.descansobygi.model;

import jakarta.persistence.*;

import lombok.*;

import java.util.HashSet;
import java.util.Set;

// Indica que esta clase se persiste como una tabla en PostgreSQL.
@Entity
@Table(name = "marcas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
// Comparar por nombre
@EqualsAndHashCode(of = {"nombre"}, callSuper = false)
public class Marca extends Base {

    // Nombre obligatorio de la marca, limitado a 100 caracteres.
    @Column(nullable = false, length = 100)
    private String nombre;

    @OneToMany(mappedBy = "marca", fetch = FetchType.LAZY)
    private Set<Producto> productos = new HashSet<>();
}
