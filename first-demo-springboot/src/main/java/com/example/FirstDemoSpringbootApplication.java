package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import com.example.ioc.Configuracion;
import com.example.ioc.Repositorio;
import com.example.ioc.RepositorioImpl;
import com.example.ioc.Servicio;
import com.example.ioc.ServicioImpl;

@SpringBootApplication
//@ComponentScan(basePackages = "com.example.ioc")
public class FirstDemoSpringbootApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(FirstDemoSpringbootApplication.class, args);
	}
	
	@Autowired
	Servicio srv;
	
//	@Autowired
//	@Qualifier("verdad")
//	Repositorio repo1;
//	@Autowired
//	@Qualifier("mentira")
//	Repositorio repo2;
	
	@Autowired
	Repositorio repo;
	
	

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		System.err.println("Aplicacion arrancada");
		//Servicio srv = new Servicio(new Repositorio(new Configuracion()));

		//AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
		//srv.guardar();
//		repo1.guardar();
//		repo2.guardar();
		repo.guardar();
	}
	
	
	
	
	
	
//	//Metodo que ejecuta acciones despues de que finaliza el arranque de la aplicación
//	@Bean
//	public CommandLineRunner demo()
//	{
//		return (args) -> {
//			System.err.println("Aplicacion Arrancadaaaaaa");
//		};
//	}
}
