package cl.nttdata.prueba_bci.dto;

import lombok.Data;

/**
 * DTO para recibir datos de teléfono en requests HTTP.
 * Contiene número telefónico y códigos de ubicación geográfica.
 * 
 * @author Patricio Ramos - NTTDATA
 * @since 2025-01-01
 * @version 1.0
 */
@Data
public class TelefonoRequestDTO {
	private String numero;
	private String codigoCiudad;
	private String codigoPais;
}
