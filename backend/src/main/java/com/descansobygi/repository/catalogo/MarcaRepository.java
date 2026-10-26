
package com.descansobygi.repository.catalogo;

import com.descansobygi.model.Marca;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;

// Proporciona las operaciones CRUD para la entidad Marca.
// Long indica el tipo de dato del identificador heredado de Base.
public interface MarcaRepository extends JpaRepository<Marca, Long> {
    boolean existsByNombre(String nombre);
}
