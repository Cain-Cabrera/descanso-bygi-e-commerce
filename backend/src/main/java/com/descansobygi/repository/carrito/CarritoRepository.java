
package com.descansobygi.repository.carrito;

import com.descansobygi.enums.EstadoCarrito;
import com.descansobygi.model.Carrito;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

// Proporciona operaciones CRUD para los carritos.
public interface CarritoRepository extends JpaRepository<Carrito, Long> {
    Optional<Carrito> findByUsuarioIdAndEstado(Long usuarioId, EstadoCarrito estado);
}