package cl.nttdata.prueba_bci.exception;

/**
 * Excepción lanzada cuando el formato del correo electrónico es inválido.
 * Extiende RuntimeException para ser una excepción no verificada.
 * 
 * @author Patricio Ramos - NTTDATA
 * @since 2025-01-01
 * @version 1.0
 */
public class FormatoCorreoInvalidoException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FormatoCorreoInvalidoException(String message) {
		super(message);
	}
}
