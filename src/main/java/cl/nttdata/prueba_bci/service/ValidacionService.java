package cl.nttdata.prueba_bci.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import cl.nttdata.prueba_bci.exception.CorreoExistenteException;
import cl.nttdata.prueba_bci.exception.FormatoCorreoInvalidoException;
import cl.nttdata.prueba_bci.repository.UsuarioRepository;

import java.util.regex.Pattern;

/**
 * Servicio para validaciones de negocio del sistema.
 * Proporciona validaciones de formato de correo, fortaleza de contraseña,
 * verificación de duplicados y encriptación segura.
 * 
 * @author Patricio Ramos - NTTDATA
 * @since 2025-01-01
 * @version 1.0
 */
@Service
public class ValidacionService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Value("${app.password.regex}")
    private String passwordRegex;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final Pattern emailPattern = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    /**
     * Valida el formato del correo electrónico y verifica que no esté duplicado.
     * 
     * @param correo correo electrónico a validar
     * @throws FormatoCorreoInvalidoException si el formato es inválido
     * @throws CorreoExistenteException si el correo ya está registrado
     */
    public void validarCorreo(String correo) {
        if (!emailPattern.matcher(correo).matches()) {
            throw new FormatoCorreoInvalidoException("Formato de correo inválido");
        }
        
        if (usuarioRepository.existsByCorreo(correo)) {
            throw new CorreoExistenteException("El correo ya está registrado");
        }
    }

    /**
     * Valida que la contraseña cumpla con los requisitos de seguridad.
     * 
     * @param contrasena contraseña a validar
     * @throws IllegalArgumentException si no cumple con el formato requerido
     */
    public void validarContrasena(String contrasena) {
//        if (!contrasena.matches(passwordRegex)) {
        if (!Pattern.matches(passwordRegex, contrasena)) {
            throw new IllegalArgumentException("La contraseña no cumple con el formato requerido");
        }
    }

    /**
     * Encripta una contraseña usando el algoritmo BCrypt.
     * 
     * @param contrasena contraseña en texto plano
     * @return contraseña encriptada con BCrypt
     */
    public String encriptarContrasena(String contrasena) {
        return passwordEncoder.encode(contrasena);
    }
}