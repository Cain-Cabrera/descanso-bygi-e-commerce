
package com.descansobygi.repository.carrito;

import com.descansobygi.model.ItemCarrito;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

// Proporciona las operaciones CRUD básicas para los ítems del carrito.
public interface ItemCarritoRepository
        extends JpaRepository<ItemCarrito, Long> {
    Optional<ItemCarrito> findByCarritoIdAndProductoIdAndEliminadoFalse(Long carritoId, Long productoId);

    List<ItemCarrito> findByCarritoIdAndEliminadoFalse(Long carritoId);
}
