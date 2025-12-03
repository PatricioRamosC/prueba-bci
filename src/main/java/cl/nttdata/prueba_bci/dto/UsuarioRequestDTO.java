package cl.nttdata.prueba_bci.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * DTO para recibir datos de usuario en requests HTTP.
 * Contiene información personal del usuario y lista de teléfonos asociados.
 * 
 * @author Patricio Ramos - NTTDATA
 * @since 2025-01-01
 * @version 1.0
 */

@Data
public class UsuarioRequestDTO {
	private String nombre;
	private String correo;
	@JsonProperty("contraseña")
	private String contrasena;
	private List<TelefonoRequestDTO> telefonos;
}
