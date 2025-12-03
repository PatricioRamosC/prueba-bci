package cl.nttdata.prueba_bci.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Configuración de seguridad de Spring Security.
 * Desactiva CSRF y permite acceso libre a todos los endpoints y la consola H2.
 * 
 * @author Patricio Ramos - NTTDATA
 * @since 2025-01-01
 * @version 1.0
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * Configura las reglas de seguridad HTTP.
     * Desactiva CSRF, permite frames para H2 console y permite acceso a todos los endpoints.
     * 
     * @param http configuración de seguridad HTTP
     * @throws Exception si ocurre un error en la configuración
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .headers().frameOptions().disable()
            .and()
            .authorizeRequests()
            .antMatchers("/h2-console/**").permitAll()
            .anyRequest().permitAll();
    }
}