
package com.descansobygi.repository;

import com.descansobygi.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

// Proporciona las operaciones CRUD para los productos.
// Long corresponde al tipo del identificador heredado de Base.
public interface ProductoRepository extends JpaRepository<Producto, Long> {
}
