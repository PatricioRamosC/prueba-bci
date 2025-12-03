package cl.nttdata.prueba_bci.dto;

import lombok.Data;

/**
 * @apiNote DTO - Que representa una clase de un teléfono del uusuaio.
 * @author pramosca - nttdata
 * @since 2025-12-01
 */
@Data
public class TelefonoRequestDTO {
	private String numero;
	private String codigoCiudad;
	private String codigoPais;
}
