package cl.nttdata.prueba_bci.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import cl.nttdata.prueba_bci.exception.CorreoExistenteException;
import cl.nttdata.prueba_bci.exception.FormatoCorreoInvalidoException;
import cl.nttdata.prueba_bci.repository.UsuarioRepository;

import java.util.regex.Pattern;

@Service
public class ValidacionService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Value("${app.password.regex}")
    private String passwordRegex;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final Pattern emailPattern = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    public void validarCorreo(String correo) {
        if (!emailPattern.matcher(correo).matches()) {
            throw new FormatoCorreoInvalidoException("Formato de correo inválido");
        }
        
        if (usuarioRepository.existsByCorreo(correo)) {
            throw new CorreoExistenteException("El correo ya está registrado");
        }
    }

    public void validarContrasena(String contrasena) {
//        if (!contrasena.matches(passwordRegex)) {
        if (!Pattern.matches(passwordRegex, contrasena)) {
            throw new IllegalArgumentException("La contraseña no cumple con el formato requerido");
        }
    }

    public String encriptarContrasena(String contrasena) {
        return passwordEncoder.encode(contrasena);
    }
}