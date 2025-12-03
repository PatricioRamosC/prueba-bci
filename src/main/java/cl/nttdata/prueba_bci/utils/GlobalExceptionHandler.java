package cl.nttdata.prueba_bci.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import cl.nttdata.prueba_bci.dto.ErrorResponseDTO;
import cl.nttdata.prueba_bci.exception.CorreoExistenteException;
import cl.nttdata.prueba_bci.exception.UsuarioInexistenteException;
import cl.nttdata.prueba_bci.exception.FormatoCorreoInvalidoException;
import cl.nttdata.prueba_bci.exception.TokenInvalidoException;

/**
 * Manejador global de excepciones para la API.
 * Captura y maneja todas las excepciones del sistema retornando respuestas HTTP apropiadas.
 * 
 * @author Patricio Ramos - NTTDATA
 * @since 2025-01-01
 * @version 1.0
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Maneja excepciones de usuario inexistente.
     * 
     * @param ex excepción de usuario inexistente
     * @return respuesta HTTP 204 No Content con mensaje de error
     */
    @ExceptionHandler(UsuarioInexistenteException.class)
    public ResponseEntity<ErrorResponseDTO> handleCorreoInexistente(UsuarioInexistenteException ex) {
        ErrorResponseDTO error = new ErrorResponseDTO(ex.getMessage());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(error);
    }

    /**
     * Maneja excepciones de correo duplicado.
     * 
     * @param ex excepción de correo existente
     * @return respuesta HTTP 409 Conflict con mensaje de error
     */
    @ExceptionHandler(CorreoExistenteException.class)
    public ResponseEntity<ErrorResponseDTO> handleCorreoDuplicado(CorreoExistenteException ex) {
        ErrorResponseDTO error = new ErrorResponseDTO(ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    /**
     * Maneja excepciones de formato de correo inválido.
     * 
     * @param ex excepción de formato de correo inválido
     * @return respuesta HTTP 400 Bad Request con mensaje de error
     */
    @ExceptionHandler(FormatoCorreoInvalidoException.class)
    public ResponseEntity<ErrorResponseDTO> handleFormatoCorreo(FormatoCorreoInvalidoException ex) {
        ErrorResponseDTO error = new ErrorResponseDTO(ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    /**
     * Maneja excepciones de contraseña inválida.
     * 
     * @param ex excepción de argumento ilegal (contraseña inválida)
     * @return respuesta HTTP 400 Bad Request con mensaje de error
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponseDTO> handleContrasenaInvalida(IllegalArgumentException ex) {
        ErrorResponseDTO error = new ErrorResponseDTO(ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    /**
     * Maneja excepciones de token JWT inválido.
     * 
     * @param ex excepción de token inválido
     * @return respuesta HTTP 401 Unauthorized con mensaje de error
     */
    @ExceptionHandler(TokenInvalidoException.class)
    public ResponseEntity<ErrorResponseDTO> handleTokenInvalido(TokenInvalidoException ex) {
        ErrorResponseDTO error = new ErrorResponseDTO(ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }

    /**
     * Maneja cualquier excepción no capturada por otros handlers.
     * 
     * @param e excepción genérica
     * @return respuesta HTTP 500 Internal Server Error con mensaje genérico
     */
    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ErrorResponseDTO> handleGeneric(Throwable e) {
        ErrorResponseDTO error = new ErrorResponseDTO("Error interno del servidor");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}
