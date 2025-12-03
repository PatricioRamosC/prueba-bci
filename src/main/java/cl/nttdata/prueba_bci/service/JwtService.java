package cl.nttdata.prueba_bci.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {

    @Value("${jwt.secret.key:mi-clave-secreta-bci-2025}")
    private String SECRET_KEY;
    @Value("${jwt.expiration.time:24}")
    private long EXPIRATION_TIME;

    public String generateToken(String email) {
        return JWT.create()
                .withSubject(email)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + (EXPIRATION_TIME * 3600 * 1000)))
                .sign(Algorithm.HMAC256(SECRET_KEY));
    }

    public boolean isTokenValid(String token, String email) {
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET_KEY)).build();
            DecodedJWT jwt = verifier.verify(token);
            System.out.println(jwt.getSubject());
            System.out.println(jwt.getExpiresAt().after(new Date()));
            return jwt.getSubject().equals(email) && jwt.getExpiresAt().after(new Date());
        } catch (JWTVerificationException e) {
        	System.out.println(e.toString());
            return false;
        }
    }
    
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