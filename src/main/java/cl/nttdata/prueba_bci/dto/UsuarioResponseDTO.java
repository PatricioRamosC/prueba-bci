package cl.nttdata.prueba_bci.dto;

import java.time.LocalDateTime;
import lombok.Data;

/**
 * DTO para enviar datos de usuario en responses HTTP.
 * Contiene información básica del usuario sin datos sensibles como contraseña.
 * 
 * @author Patricio Ramos - NTTDATA
 * @since 2025-01-01
 * @version 1.0
 */
@Data
public class UsuarioResponseDTO {

    private String id;
    private LocalDateTime creado;
    private LocalDateTime modificado;
    private LocalDateTime ultimoLogin;
    private String token;
    private Boolean activo;

}
