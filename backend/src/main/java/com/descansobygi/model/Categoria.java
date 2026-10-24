
package com.descansobygi.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

// Indica que la clase representa una entidad persistente.
@Entity

// Establece el nombre de tabla definido en el modelo ER.
@Table(name = "categorias")

// Lombok genera automáticamente los getters y setters.
@Getter
@Setter

// Constructor vacío requerido por JPA.
@NoArgsConstructor

// Constructor con los atributos propios de Categoria: nombre y descripcion.
@AllArgsConstructor
public class Categoria extends Base {

    // Nombre obligatorio de la categoría, con un máximo de 100 caracteres.
    @Column(nullable = false, length = 100)
    private String nombre;

    // Descripción opcional, con un máximo de 255 caracteres.
    @Column(length = 255)
    private String descripcion;

    // No necesitamos escribir manualmente los getters y setters:
    // Lombok los genera durante la compilación.
}
