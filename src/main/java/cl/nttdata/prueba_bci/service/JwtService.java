package cl.nttdata.prueba_bci.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Servicio para la gestión de tokens JWT (JSON Web Tokens).
 * Proporciona funcionalidades para generar y validar tokens de autenticación
 * con algoritmo HMAC256 y expiración configurable.
 * 
 * @author Patricio Ramos - NTTDATA
 * @since 2025-01-01
 * @version 1.0
 */
@Service
public class JwtService {

    @Value("${jwt.secret.key:mi-clave-secreta-bci-2025}")
    private String SECRET_KEY;
    @Value("${jwt.expiration.time:24}")
    private long EXPIRATION_TIME;

    /**
     * Genera un token JWT para el usuario especificado.
     * 
     * @param email correo electrónico del usuario para incluir como subject
     * @return token JWT firmado con expiración de 24 horas
     */
    public String generateToken(String email) {
        return JWT.create()
                .withSubject(email)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + (EXPIRATION_TIME * 3600 * 1000)))
                .sign(Algorithm.HMAC256(SECRET_KEY));
    }

    /**
     * Valida si un token JWT es válido y corresponde al email especificado.
     * 
     * @param token token JWT a validar
     * @param email correo electrónico esperado en el subject del token
     * @return true si el token es válido y no ha expirado, false en caso contrario
     */
    public boolean isTokenValid(String token, String email) {
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET_KEY)).build();
            DecodedJWT jwt = verifier.verify(token);
            return jwt.getSubject().equals(email) && jwt.getExpiresAt().after(new Date());
        } catch (JWTVerificationException e) {
            return false;
        }
    }
    
    /**
     * Limpia el token JWT removiendo el prefijo "Bearer" si existe.
     * 
     * @param tokenRequest token con posible prefijo "Bearer"
     * @return token limpio sin prefijo, o null si el input es null
     */
    public static String cleanToken(String tokenRequest) {
    	if (tokenRequest == null) {
    		return null;
    	}
        try {
        	String []campos = tokenRequest.split(" ");
        	return campos[campos.length - 1];
        } catch (JWTVerificationException e) {
            return null;
        }
    }
    
}