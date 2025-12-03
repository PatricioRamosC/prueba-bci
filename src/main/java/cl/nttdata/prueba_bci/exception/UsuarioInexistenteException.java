package cl.nttdata.prueba_bci.exception;

public class UsuarioInexistenteException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public UsuarioInexistenteException(String message) {
		super(message);
	}

}
