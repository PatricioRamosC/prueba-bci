/**
 * 
 */
package cl.nttdata.prueba_bci.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.nttdata.prueba_bci.entity.UsuarioModel;

/**
 * @apiNote	Se crea el repository de Usuario.
 * @author 	pramosca - nttdata
 * @since 	2023-12-01
 */
public interface UsuarioRepository extends JpaRepository<UsuarioModel, UUID> {

    // Búsqueda por correo (para validar que no esté duplicado)
    Optional<UsuarioModel> findByCorreo(String correo);

    // Opcional: saber si existe un usuario con cierto correo
    boolean existsByCorreo(String correo);
}
