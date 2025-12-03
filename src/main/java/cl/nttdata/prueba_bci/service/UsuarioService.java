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

/**
 * Servicio para la gestión de usuarios del sistema.
 * Proporciona operaciones CRUD con validaciones de seguridad, encriptación de contraseñas
 * y manejo de tokens JWT para autenticación.
 * 
 * @author Patricio Ramos - NTTDATA
 * @since 2025-01-01
 * @version 1.0
 */
@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private JwtService jwtService;
    
    @Autowired
    private ValidacionService validacionService;
    
    /**
     * Crea un nuevo usuario en el sistema con validaciones de correo y contraseña.
     * Genera un token JWT y encripta la contraseña antes de almacenar.
     * 
     * @param request estructura completa del usuario y teléfonos asociados
     * @return UsuarioResponseDTO con los datos del usuario creado y el token generado
     * @throws CorreoExistenteException si el correo ya está registrado
     * @throws IllegalArgumentException si la contraseña no cumple el formato requerido
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
    
    /**
     * Lista todos los usuarios registrados en el sistema.
     * 
     * @return Lista de UsuarioResponseDTO con la información básica de todos los usuarios
     */
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
    
    /**
     * Actualiza los datos de un usuario existente.
     * Valida el token JWT antes de permitir la actualización.
     * 
     * @param request datos del usuario a actualizar
     * @param token token JWT para validar la autorización
     * @return UsuarioResponseDTO con los datos actualizados
     * @throws TokenInvalidoException si el token es inválido o expirado
     * @throws UsuarioInexistenteException si el usuario no existe
     */
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
    
    /**
     * Elimina un usuario del sistema.
     * Valida el token JWT antes de permitir la eliminación.
     * 
     * @param request datos del usuario a eliminar (requiere correo)
     * @param token token JWT para validar la autorización
     * @return UsuarioResponseDTO con el estado final del usuario eliminado
     * @throws TokenInvalidoException si el token es inválido o expirado
     * @throws UsuarioInexistenteException si el usuario no existe
     */
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
     * Valida que el token JWT sea válido y corresponda al email proporcionado.
     * 
     * @param token token JWT a validar
     * @param email correo electrónico asociado al token
     * @throws TokenInvalidoException si el token es inválido, expirado o no corresponde al email
     */
    private void validarToken(String token, String email) {
        if (!jwtService.isTokenValid(token, email)) {
            throw new TokenInvalidoException("Token inválido");
        }
    }
    
    /**
     * Persiste un usuario en la base de datos y retorna la respuesta formateada.
     * 
     * @param usuario entidad del usuario a guardar
     * @return UsuarioResponseDTO con los datos del usuario guardado
     */
    private UsuarioResponseDTO grabarUsuario(UsuarioModel usuario) {
        UsuarioModel guardado = usuarioRepository.saveAndFlush(usuario);
        return crearResponse(guardado);
    }
    
    /**
     * Convierte una entidad UsuarioModel a UsuarioResponseDTO.
     * 
     * @param usuario entidad del usuario a convertir
     * @return UsuarioResponseDTO con los datos básicos del usuario
     */
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
     * Asigna los teléfonos del request al usuario, reemplazando los existentes.
     * Crea nuevas entidades TelefonoModel y las asocia al usuario.
     * 
     * @param request datos del request que contiene la lista de teléfonos
     * @param usuario entidad del usuario al que se asignarán los teléfonos
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

    /**
     * Busca un usuario por su correo electrónico.
     * 
     * @param correo correo electrónico del usuario a buscar
     * @return UsuarioModel encontrado
     * @throws UsuarioInexistenteException si no se encuentra el usuario
     */
    private UsuarioModel buscarUsuario(String correo) {
        // Buscar usuario por correo
        return usuarioRepository.findByCorreo(correo)
                .orElseThrow(() -> new UsuarioInexistenteException("Usuario no encontrado"));
    }
}
