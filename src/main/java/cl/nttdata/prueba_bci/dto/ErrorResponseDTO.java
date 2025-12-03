package cl.nttdata.prueba_bci.dto;

import lombok.Data;

/**
 * DTO para respuestas de error en la API.
 * Encapsula mensajes de error para ser retornados al cliente.
 * 
 * @author Patricio Ramos - NTTDATA
 * @since 2025-01-01
 * @version 1.0
 */
@Data
public class ErrorResponseDTO {

	private String mensaje;
	
	public ErrorResponseDTO(String mensaje) {
		this.mensaje = mensaje;
	}
}
