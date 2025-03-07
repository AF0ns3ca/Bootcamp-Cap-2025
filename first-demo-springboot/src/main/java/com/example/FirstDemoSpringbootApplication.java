package com.example;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class FirstDemoSpringbootApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(FirstDemoSpringbootApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		System.err.println("Aplicacion arrancada");
	}
	
	//Metodo que ejecuta acciones despues de que finaliza el arranque de la aplicación
	@Bean
	public CommandLineRunner demo()
	{
		return (args) -> {
			System.err.println("Aplicacion Arrancadaaaaaa");
		};
	}
}
