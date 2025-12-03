package cl.nttdata.prueba_bci.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.nttdata.prueba_bci.dto.UsuarioRequestDTO;
import cl.nttdata.prueba_bci.dto.UsuarioResponseDTO;
import cl.nttdata.prueba_bci.entity.TelefonoModel;
import cl.nttdata.prueba_bci.entity.UsuarioModel;
import cl.nttdata.prueba_bci.repository.UsuarioRepository;

import cl.nttdata.prueba_bci.exception.TokenInvalidoException;
import cl.nttdata.prueba_bci.exception.UsuarioInexistenteException;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private JwtService jwtService;
    
    @Autowired
    private ValidacionService validacionService;
    
    /**
     * 
     * @param 	UsuarioRequestDTO - estructura completa del usuario y teléfonos asociados.
     * @return	UsuarioResponseDTO
     */
    @Transactional
    public UsuarioResponseDTO crearUsuario(UsuarioRequestDTO request) {
        // Validaciones
        validacionService.validarCorreo(request.getCorreo());
        validacionService.validarContrasena(request.getContrasena());
        
        String token = jwtService.generateToken(request.getCorreo());
        
        LocalDateTime ahora = LocalDateTime.now();
        UsuarioModel usuario = new UsuarioModel();

        usuario.setToken(token);
        usuario.setCreado(ahora);
        usuario.setCorreo(request.getCorreo());

        usuario.setNombre(request.getNombre());
        usuario.setContrasena(validacionService.encriptarContrasena(request.getContrasena()));
        usuario.setActivo(true);
        usuario.setModificado(ahora);
        usuario.setUltimoLogin(ahora);

        setearTelefonos(request, usuario);
        
        UsuarioResponseDTO response = grabarUsuario(usuario);
        return response;
    }
    
    public List<UsuarioResponseDTO> listarUsuarios() {
        return usuarioRepository.findAll().stream()
            .map(usuario -> {
                UsuarioResponseDTO usuarioResponse = new UsuarioResponseDTO();
                usuarioResponse.setId(usuario.getId());
                usuarioResponse.setCreado(usuario.getCreado());
                usuarioResponse.setModificado(usuario.getModificado());
                usuarioResponse.setUltimoLogin(usuario.getUltimoLogin());
                usuarioResponse.setToken(usuario.getToken());
                usuarioResponse.setActivo(usuario.getActivo());
                return usuarioResponse;
            }).collect(Collectors.toList());
    }
    
    @Transactional
    public UsuarioResponseDTO actualizarUsuario(UsuarioRequestDTO request, String token) {
        validarToken(token, request.getCorreo());
        
        UsuarioModel usuario = buscarUsuario(request.getCorreo());
        
        // Actualizar campos directamente en la entidad gestionada
        usuario.setNombre(request.getNombre());
        usuario.setContrasena(validacionService.encriptarContrasena(request.getContrasena()));
        usuario.setModificado(LocalDateTime.now());
        usuario.setUltimoLogin(LocalDateTime.now());
        
        setearTelefonos(request, usuario);
        
        return grabarUsuario(usuario);
    }
    
    @Transactional
    public UsuarioResponseDTO eliminarUsuario(UsuarioRequestDTO request, String token) {
        validarToken(token, request.getCorreo());
        UsuarioModel usuario = buscarUsuario(request.getCorreo());
        usuarioRepository.delete(usuario);
        UsuarioResponseDTO response = crearResponse(usuario);
        
        response.setActivo(false);
        response.setToken(null);
        
        return response; 
    }

    /**
     * En caso de que el token no sea válido, se lanza una excepción.
     * @param token
     * @param email
     */
    private void validarToken(String token, String email) {
        if (!jwtService.isTokenValid(token, email)) {
            throw new TokenInvalidoException("Token inválido");
        }
    }
    
    private UsuarioResponseDTO grabarUsuario(UsuarioModel usuario) {
        UsuarioModel guardado = usuarioRepository.saveAndFlush(usuario);
        return crearResponse(guardado);
    }
    
    private UsuarioResponseDTO crearResponse(UsuarioModel usuario) {
        UsuarioResponseDTO response = new UsuarioResponseDTO();
        response.setId(usuario.getId());
        response.setCreado(usuario.getCreado());
        response.setModificado(usuario.getModificado());
        response.setUltimoLogin(usuario.getUltimoLogin());
        response.setToken(usuario.getToken());
        response.setActivo(usuario.getActivo());
        
        return response;
    }

    /**
     * Toma todos los teléfonos que vienen desde el request y los traspasa a la estructura de TelefonoModel del usuario. 
     * @param request
     * @param usuario
     */
    private void setearTelefonos(UsuarioRequestDTO request, UsuarioModel usuario) {
        List<TelefonoModel> telefonos = new ArrayList<>();
        if (usuario.getTelefonos() != null) {
        	telefonos = usuario.getTelefonos();
        }
        telefonos.clear();
        
        if (request.getTelefonos() != null) {
        	request.getTelefonos().stream().map(x -> {
        		TelefonoModel telefono = new TelefonoModel();
        		telefono.setCodigoPais(x.getCodigoPais());
        		telefono.setCodigoCiudad(x.getCodigoCiudad());
        		telefono.setNumero(x.getNumero());
        		telefono.setUsuario(usuario);
        		return telefono;
        	}).collect(Collectors.toList()).forEach(telefonos::add);
        }
        usuario.setTelefonos(telefonos);
    }

    private UsuarioModel buscarUsuario(String correo) {
        // Buscar usuario por correo
        return usuarioRepository.findByCorreo(correo)
                .orElseThrow(() -> new UsuarioInexistenteException("Usuario no encontrado"));
    }
}
