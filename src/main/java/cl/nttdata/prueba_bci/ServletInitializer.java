package cl.nttdata.prueba_bci;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * Inicializador de servlet para despliegue en contenedores de aplicaciones web.
 * Permite que la aplicación Spring Boot se ejecute en servidores como Tomcat.
 * 
 * @author Patricio Ramos - NTTDATA
 * @since 2025-01-01
 * @version 1.0
 */
public class ServletInitializer extends SpringBootServletInitializer {

	/**
	 * Configura la aplicación para despliegue en servlet container.
	 * 
	 * @param application builder de la aplicación Spring Boot
	 * @return aplicación configurada
	 */
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(PruebaBciApplication.class);
	}

}
