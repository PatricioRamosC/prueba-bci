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

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Usuario inexistente → 204 No content
    @ExceptionHandler(UsuarioInexistenteException.class)
    public ResponseEntity<ErrorResponseDTO> handleCorreoInexistente(UsuarioInexistenteException ex) {
        ErrorResponseDTO error = new ErrorResponseDTO(ex.getMessage());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(error);
    }

    // Correo duplicado → 409 Conflict
    @ExceptionHandler(CorreoExistenteException.class)
    public ResponseEntity<ErrorResponseDTO> handleCorreoDuplicado(CorreoExistenteException ex) {
        ErrorResponseDTO error = new ErrorResponseDTO(ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    // Formato correo inválido → 400 Bad Request
    @ExceptionHandler(FormatoCorreoInvalidoException.class)
    public ResponseEntity<ErrorResponseDTO> handleFormatoCorreo(FormatoCorreoInvalidoException ex) {
        ErrorResponseDTO error = new ErrorResponseDTO(ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    // Contraseña inválida → 400 Bad Request
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponseDTO> handleContrasenaInvalida(IllegalArgumentException ex) {
        ErrorResponseDTO error = new ErrorResponseDTO(ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    // Token inválido → 401 Unauthorized
    @ExceptionHandler(TokenInvalidoException.class)
    public ResponseEntity<ErrorResponseDTO> handleTokenInvalido(TokenInvalidoException ex) {
        ErrorResponseDTO error = new ErrorResponseDTO(ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }

    // Fallback genérico → 500 Internal Server Error
    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ErrorResponseDTO> handleGeneric(Throwable e) {
        ErrorResponseDTO error = new ErrorResponseDTO("Error interno del servidor");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}
