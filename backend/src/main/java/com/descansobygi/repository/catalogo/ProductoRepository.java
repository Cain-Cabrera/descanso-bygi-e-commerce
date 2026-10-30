
package com.descansobygi.repository.catalogo;

import com.descansobygi.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

// Proporciona las operaciones CRUD para los productos.
// Long corresponde al tipo del identificador heredado de Base.
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    @Query("SELECT p FROM Producto p WHERE LOWER(p.nombre) LIKE LOWER(CONCAT('%', :nombre, '%')) AND p.eliminado = false")
    List<Producto> findByNombreActivo(@Param("nombre") String nombre);
    @Query("SELECT p FROM Producto p WHERE p.categoria.id = :categoriaId AND p.eliminado = false")
    List<Producto> findByCategoriaIdActivo(@Param("categoriaId") Long categoriaId);
    List<Producto> findByMarcaIdAndEliminadoFalse(Long marcaId);
}
