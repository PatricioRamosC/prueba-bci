package cl.nttdata.prueba_bci.dto;

import lombok.Data;

@Data
public class ErrorResponseDTO {

	private String mensaje;
	
	public ErrorResponseDTO(String mensaje) {
		this.mensaje = mensaje;
	}
}
