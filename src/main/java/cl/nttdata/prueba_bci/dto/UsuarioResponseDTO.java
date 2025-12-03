package cl.nttdata.prueba_bci.dto;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class UsuarioResponseDTO {

    private String id;
    private LocalDateTime creado;
    private LocalDateTime modificado;
    private LocalDateTime ultimoLogin;
    private String token;
    private Boolean activo;

}
