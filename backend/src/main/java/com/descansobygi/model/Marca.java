
package com.descansobygi.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// Indica que esta clase se persiste como una tabla en PostgreSQL.
@Entity
@Table(name = "marcas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Marca extends Base {

    // Nombre obligatorio de la marca, limitado a 100 caracteres.
    @Column(nullable = false, length = 100)
    private String nombre;
}
