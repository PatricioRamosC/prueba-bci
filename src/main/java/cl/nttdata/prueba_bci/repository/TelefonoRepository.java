package cl.nttdata.prueba_bci.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.nttdata.prueba_bci.entity.TelefonoModel;

/** 
 * @apiNote Se crea el repositorio de TelefonoModel
 * @author 	pramosca - nttdata
 * @since 	2023-12-01
 */
public interface TelefonoRepository extends JpaRepository<TelefonoModel, Long> {

}
