package com.example;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
//Añadir el RestController
@RestController
public class CatalogoSpringApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(CatalogoSpringApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		System.err.println("Aplicacion arrancada");
	}

	//Metodo que ejecuta acciones despues de que finaliza el arranque de la aplicación
//		@Bean
//		public CommandLineRunner demo()
//		{
//			return (args) -> {
//				System.err.println("Aplicacion Arrancadaaaaaa");
//			};
//		}
		
		//Lugar al que apunta la pagina, en este caso la pagina por defecto
		//http://localhost:8003/?name=Alvaro Pasando el nombre como parametro
		@GetMapping("/")
	    public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
	      return String.format("Hello %s!", name);
	    }

}
