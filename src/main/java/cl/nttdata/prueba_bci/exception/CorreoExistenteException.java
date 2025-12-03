package cl.nttdata.prueba_bci.exception;

/**
 * Excepción lanzada cuando se intenta registrar un usuario con un correo que ya existe.
 * Extiende RuntimeException para ser una excepción no verificada.
 * 
 * @author Patricio Ramos - NTTDATA
 * @since 2025-01-01
 * @version 1.0
 */
public class CorreoExistenteException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CorreoExistenteException(String message) {
		super(message);
	}
}
