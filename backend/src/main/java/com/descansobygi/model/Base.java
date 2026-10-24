
package com.descansobygi.model;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

// @MappedSuperclass indica que esta clase aporta atributos a sus subclases,
// pero no representa una tabla propia en PostgreSQL.
@MappedSuperclass

// Lombok genera automáticamente los métodos getter para consultar los campos.
@Getter

// Lombok genera automáticamente los métodos setter para modificar los campos.
@Setter

// Genera el constructor vacío que JPA necesita para crear las entidades.
@NoArgsConstructor
public abstract class Base {

    // Identificador único de cada registro.
    // PostgreSQL generará el valor al insertar una fila.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Fecha y hora de creación del registro.
    // Una vez insertada, no podrá modificarse mediante las actualizaciones JPA.
    @Column(name = "fecha_alta", nullable = false, updatable = false)
    private LocalDateTime fechaAlta;

    // Indicador de eliminación lógica:
    // false = registro activo; true = registro marcado como eliminado.
    @Column(nullable = false)
    private boolean eliminado = false;

    // Se ejecuta automáticamente antes de insertar una entidad nueva.
    @PrePersist
    protected void antesDeGuardar() {

        // Asignamos la fecha solamente si todavía no tiene un valor.
        if (fechaAlta == null) {
            fechaAlta = LocalDateTime.now();
        }
    }
}
