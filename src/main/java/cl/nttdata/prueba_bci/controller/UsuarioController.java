package cl.nttdata.prueba_bci.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.nttdata.prueba_bci.dto.UsuarioRequestDTO;
import cl.nttdata.prueba_bci.dto.UsuarioResponseDTO;
import cl.nttdata.prueba_bci.service.JwtService;
import cl.nttdata.prueba_bci.service.UsuarioService;

/**
 * Controlador REST para la gestión de usuarios.
 * Proporciona endpoints para operaciones CRUD de usuarios con autenticación JWT.
 * 
 * @author Patricio Ramos - NTTDATA
 * @since 2025-01-01
 * @version 1.0
 */
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;
	
	/**
	 * Lista todos los usuarios registrados en el sistema.
	 * 
	 * @return ResponseEntity con lista de usuarios
	 */
	@GetMapping
	public ResponseEntity<?> getUsuarios() {
		List<UsuarioResponseDTO> response = usuarioService.listarUsuarios();
	    return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	/**
	 * Crea un nuevo usuario en el sistema.
	 * 
	 * @param request datos del usuario a crear
	 * @param token token de autorización (opcional)
	 * @return ResponseEntity con datos del usuario creado y token JWT
	 */
	@PostMapping
	public ResponseEntity<?> postUsuarios(@RequestBody UsuarioRequestDTO request,
			@RequestHeader(name = "Authorization", required = false) String token) {
		UsuarioResponseDTO response = usuarioService.crearUsuario(request);
	    return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	/**
	 * Modifica un usuario en el sistema.
	 *  
	 * @param request datos del usuario a crear
	 * @param token token de autorización (opcional)
	 * @return ResponseEntity con datos del usuario creado y token JWT
	 */
	@PutMapping
	public ResponseEntity<?> putUsuarios(@RequestBody UsuarioRequestDTO request,
			@RequestHeader(name = "Authorization", required = false) String token) {
		UsuarioResponseDTO response = usuarioService.actualizarUsuario(request, JwtService.cleanToken(token));
	    return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	/**
	 * Modifica un usuario en el sistema.
	 * 
	 * @param request datos del usuario a crear
	 * @param token token de autorización (opcional)
	 * @return ResponseEntity con datos del usuario creado y token JWT
	 */
	@PatchMapping
	public ResponseEntity<?> patchUsuarios(@RequestBody UsuarioRequestDTO request,
			@RequestHeader(name = "Authorization", required = false) String token) {
		UsuarioResponseDTO response = usuarioService.actualizarUsuario(request, JwtService.cleanToken(token));
	    return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	/**
	 * Elimina un usuario del sistema.
	 * 
	 * @param request datos del usuario a crear
	 * @param token token de autorización (opcional)
	 * @return ResponseEntity con datos del usuario creado y token JWT
	 */
	@DeleteMapping
	public ResponseEntity<?> deleteUsuarios(@RequestBody UsuarioRequestDTO request,
			@RequestHeader(name = "Authorization", required = false) String token) {
		UsuarioResponseDTO response = usuarioService.eliminarUsuario(request, JwtService.cleanToken(token));
	    return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
}
