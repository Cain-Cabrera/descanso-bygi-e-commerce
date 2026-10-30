
package com.descansobygi.repository.usuarios;

import com.descansobygi.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

// Proporciona las operaciones CRUD para la entidad Usuario.
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    boolean existsByMail(String mail);  // Para registro (verificar duplicado)
    @Query("SELECT u FROM Usuario u WHERE u.mail = :mail AND u.eliminado = false")
    Optional<Usuario> findByMailActivo(@Param("mail") String mail); // Para login
}
