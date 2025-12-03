package cl.nttdata.prueba_bci.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**	
 * @apiNote DTO - Que representa un usuario 
 * @author pramosca - nttdata
 * @since 2025-12-01
 */

@Data
public class UsuarioRequestDTO {
	private String nombre;
	private String correo;
	@JsonProperty("contraseña")
	private String contrasena;
	private List<TelefonoRequestDTO> telefonos;
}
