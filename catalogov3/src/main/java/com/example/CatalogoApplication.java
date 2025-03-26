package com.example;

import java.util.TreeMap;
import java.util.logging.Logger;

import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.data.web.config.EnableSpringDataWebSupport.PageSerializationMode;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import jakarta.transaction.Transactional;

@EnableSpringDataWebSupport(pageSerializationMode = PageSerializationMode.VIA_DTO)
@EnableAspectJAutoProxy
@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Microservicio: Catalogo", version = "1.0", description = "**Catalogo** de Microservicios.", 
					license = @License(name = "Apache 2.0", 
					url = "https://www.apache.org/licenses/LICENSE-2.0.html"), 
					contact = @Contact(name = "Alvaro Fonseca", url = "https://github.com/AF0ns3ca", 
					email = "support@example.com")), 
					externalDocs = @ExternalDocumentation(description = "Documentación del proyecto", url = "https://github.com/AF0ns3ca/curso"))

public class CatalogoApplication implements CommandLineRunner {

	@Bean
	public OpenApiCustomizer sortSchemasAlphabetically() {
		return openApi -> {
			var schemas = openApi.getComponents().getSchemas();
			openApi.getComponents().setSchemas(new TreeMap<>(schemas));
		};
	}

	private final Logger log = Logger.getLogger(getClass().getName());

	public static void main(String[] args) {
		SpringApplication.run(CatalogoApplication.class, args);
	}

	@Override
	@Transactional
	public void run(String... args) throws Exception {
		System.err.println("Aplicación arrancada...");
		log.info("Aplicacion arrancada...");
	}

	

}
