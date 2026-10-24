
package com.descansobygi.repository;

import com.descansobygi.model.Imagen;
import org.springframework.data.jpa.repository.JpaRepository;

// Proporciona operaciones CRUD para las imágenes.
public interface ImagenRepository extends JpaRepository<Imagen, Long> {
}
