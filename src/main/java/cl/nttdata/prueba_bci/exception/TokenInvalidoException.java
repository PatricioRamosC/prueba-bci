package cl.nttdata.prueba_bci.exception;

/**
 * Excepción lanzada cuando un token JWT es inválido o ha expirado.
 * Extiende RuntimeException para ser una excepción no verificada.
 * 
 * @author Patricio Ramos - NTTDATA
 * @since 2025-01-01
 * @version 1.0
 */
public class TokenInvalidoException extends RuntimeException {
    /**
     * Constructor que recibe el mensaje de error.
     * 
     * @param message mensaje descriptivo del error
     */
    public TokenInvalidoException(String message) {
        super(message);
    }
}