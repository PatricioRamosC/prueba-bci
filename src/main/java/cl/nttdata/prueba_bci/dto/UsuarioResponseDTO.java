package cl.nttdata.prueba_bci.dto;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Data;

@Data
public class UsuarioResponseDTO {

    private UUID id;
    private LocalDateTime creado;
    private LocalDateTime modificado;
    private LocalDateTime ultimoLogin;
    private String token;
    private Boolean activo;

}
