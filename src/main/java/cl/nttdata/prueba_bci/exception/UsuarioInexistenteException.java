package cl.nttdata.prueba_bci.exception;

/**
 * Excepción lanzada cuando se intenta acceder a un usuario que no existe.
 * Extiende RuntimeException para ser una excepción no verificada.
 * 
 * @author Patricio Ramos - NTTDATA
 * @since 2025-01-01
 * @version 1.0
 */
public class UsuarioInexistenteException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor que recibe el mensaje de error.
	 * 
	 * @param message mensaje descriptivo del error
	 */
	public UsuarioInexistenteException(String message) {
		super(message);
	}

}
