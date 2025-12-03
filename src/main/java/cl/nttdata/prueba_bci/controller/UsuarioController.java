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

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;
	
	@GetMapping
	public ResponseEntity<?> getUsuarios() {
		List<UsuarioResponseDTO> response = usuarioService.listarUsuarios();
	    return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	@PostMapping
	public ResponseEntity<?> postUsuarios(@RequestBody UsuarioRequestDTO request,
			@RequestHeader(name = "Authorization", required = false) String token) {
		UsuarioResponseDTO response = usuarioService.crearUsuario(request);
	    return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@PutMapping
	public ResponseEntity<?> putUsuarios(@RequestBody UsuarioRequestDTO request,
			@RequestHeader(name = "Authorization", required = false) String token) {
		UsuarioResponseDTO response = usuarioService.actualizarUsuario(request, JwtService.cleanToken(token));
	    return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@PatchMapping
	public ResponseEntity<?> patchUsuarios(@RequestBody UsuarioRequestDTO request,
			@RequestHeader(name = "Authorization", required = false) String token) {
		UsuarioResponseDTO response = usuarioService.actualizarUsuario(request, JwtService.cleanToken(token));
	    return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@DeleteMapping
	public ResponseEntity<?> deleteUsuarios(@RequestBody UsuarioRequestDTO request,
			@RequestHeader(name = "Authorization", required = false) String token) {
		UsuarioResponseDTO response = usuarioService.eliminarUsuario(request, JwtService.cleanToken(token));
	    return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
}
