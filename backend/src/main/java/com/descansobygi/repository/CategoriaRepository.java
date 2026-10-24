
package com.descansobygi.repository;

// Importamos la entidad sobre la que trabajará el repositorio.
import com.descansobygi.model.Categoria;

// JpaRepository proporciona las operaciones de persistencia habituales.
import org.springframework.data.jpa.repository.JpaRepository;

// Este repositorio permite interactuar con la tabla categorias.
// Categoria es la entidad administrada y Long es el tipo de su identificador.
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    // No necesitamos implementar los métodos CRUD básicos.
    // Spring Data JPA los proporciona automáticamente:
    // save()        -> guardar o actualizar una categoría.
    // findById()    -> buscar una categoría por su ID.
    // findAll()     -> consultar todas las categorías.
    // deleteById()  -> eliminar físicamente una categoría por su ID.
    //
    // Importante: en nuestro proyecto utilizaremos eliminación lógica
    // cuando corresponda, mediante el atributo eliminado de Base.
}
