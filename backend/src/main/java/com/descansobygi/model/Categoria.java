
package com.descansobygi.model;

import jakarta.persistence.*;

import lombok.*;

import java.util.HashSet;
import java.util.Set;

// Indica que la clase representa una entidad persistente.
@Entity

// Establece el nombre de tabla definido en el modelo ER.
@Table(name = "categorias")

// Lombok genera automáticamente los getters y setters.
@Getter
@Setter

// Constructor vacío requerido por JPA.
@NoArgsConstructor

// Comparar por nombre
@EqualsAndHashCode(of = {"nombre"}, callSuper = false)
// Constructor con los atributos propios de Categoria: nombre y descripcion.
@AllArgsConstructor
public class Categoria extends Base {

    // Nombre obligatorio de la categoría, con un máximo de 100 caracteres.
    @Column(nullable = false, length = 100)
    private String nombre;

    // Descripción opcional, con un máximo de 255 caracteres.
    @Column(length = 255)
    private String descripcion;

    @OneToMany(mappedBy = "categoria", fetch = FetchType.LAZY)
    private Set<Producto> productos = new HashSet<>();
    // No necesitamos escribir manualmente los getters y setters:
    // Lombok los genera durante la compilación.
}
