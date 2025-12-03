package cl.nttdata.prueba_bci;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Clase principal de la aplicación Spring Boot.
 * API REST para gestión de usuarios con autenticación JWT.
 * 
 * @author Patricio Ramos - NTTDATA
 * @since 2025-01-01
 * @version 1.0
 */
@SpringBootApplication
public class PruebaBciApplication {

	/**
	 * Método principal que inicia la aplicación Spring Boot.
	 * 
	 * @param args argumentos de línea de comandos
	 */
	public static void main(String[] args) {
		SpringApplication.run(PruebaBciApplication.class, args);
	}

}
