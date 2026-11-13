
package com.descansobygi.repository.catalogo;

import com.descansobygi.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import jakarta.persistence.LockModeType;

// Proporciona las operaciones CRUD para los productos.
// Long corresponde al tipo del identificador heredado de Base.
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    long countByCategoriaIdAndEliminadoFalse(Long categoriaId);
    long countByMarcaIdAndEliminadoFalse(Long marcaId);
    // El bloqueo de fila evita que dos pedidos simultáneos consuman el mismo stock.
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT p FROM Producto p WHERE p.id = :id AND p.eliminado = false")
    Optional<Producto> findActivoPorIdParaActualizar(@Param("id") Long id);

    @Query("SELECT p FROM Producto p WHERE LOWER(p.nombre) LIKE LOWER(CONCAT('%', :nombre, '%')) AND p.eliminado = false")
    List<Producto> findByNombreActivo(@Param("nombre") String nombre);
    @Query("SELECT p FROM Producto p WHERE p.categoria.id = :categoriaId AND p.eliminado = false")
    List<Producto> findByCategoriaIdActivo(@Param("categoriaId") Long categoriaId);
    List<Producto> findByMarcaIdAndEliminadoFalse(Long marcaId);
}
