
package com.descansobygi.repository.carrito;

import com.descansobygi.model.Carrito;
import org.springframework.data.jpa.repository.JpaRepository;

// Proporciona operaciones CRUD para los carritos.
public interface CarritoRepository extends JpaRepository<Carrito, Long> {
}
