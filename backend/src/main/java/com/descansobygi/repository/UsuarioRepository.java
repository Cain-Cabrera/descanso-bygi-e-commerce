
package com.descansobygi.repository;

import com.descansobygi.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

// Proporciona las operaciones CRUD para la entidad Usuario.
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Comprueba si ya existe un usuario con ese correo electrónico.
    // Será útil para evitar registros con correos duplicados.
    boolean existsByMail(String mail);
}
