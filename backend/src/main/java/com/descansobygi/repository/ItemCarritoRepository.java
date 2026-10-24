
package com.descansobygi.repository;

import com.descansobygi.model.ItemCarrito;
import org.springframework.data.jpa.repository.JpaRepository;

// Proporciona las operaciones CRUD básicas para los ítems del carrito.
public interface ItemCarritoRepository
        extends JpaRepository<ItemCarrito, Long> {
}
