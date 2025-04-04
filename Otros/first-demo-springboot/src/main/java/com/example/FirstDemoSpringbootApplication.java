package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Sort;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import com.example.domains.contracts.repositories.ActorRepository;
import com.example.ioc.Configuracion;
import com.example.ioc.Rango;
import com.example.ioc.Repositorio;
import com.example.ioc.RepositorioImpl;
import com.example.ioc.Servicio;
import com.example.ioc.ServicioImpl;
import com.example.util.Calculadora;

@SpringBootApplication
//@ComponentScan(basePackages = "com.example.ioc")
public class FirstDemoSpringbootApplication implements CommandLineRunner {
	
	@Autowired
	private ActorRepository dao;

	public static void main(String[] args) {
		SpringApplication.run(FirstDemoSpringbootApplication.class, args);
	}
	
	private void ejemplosPruebas() {
		var calc = new Calculadora();
		System.err.println("Suma: " + calc.suma(2, 3));
		
	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		ejemplosDatos();
		
	}
	
	private void ejemplosDatos(){
		//Le ponemos como clave primaria 0 para que no haya problemas con la base de datos al insertar y lo cree automaticamente
		// var actor = new Actor(0, "Pepito", "Grillo", null);
		// dao.save(actor);
		var item = dao.findById(201);
		//Para asegurar de no mandar un null
		if(item.isPresent()){
			//Extraccion del actor para trabajar con el
			var actor = item.get();
			actor.setFirstName("VINICIUS");
			actor.setLastName(actor.getLastName().toUpperCase());
			dao.save(actor);
		} else {
			System.err.println("No se ha encontrado el actor");
		}
		dao.findAll().forEach(System.err::println);
		dao.deleteById(201);
		System.err.println("Borrado");
		dao.findAll().forEach(System.err::println);
	}
	
	private void ejemplosConsultas(){
		dao.findTop5ByFirstNameStartingWithOrderByLastNameDesc("P").forEach(System.err::println);

	}
	
//	@Autowired
//	Servicio srv;
//	
//	@Autowired
//	Rango rango;
//	
////	@Autowired
////	@Qualifier("verdad")
////	Repositorio repo1;
////	@Autowired
////	@Qualifier("mentira")
////	Repositorio repo2;
//	
//	@Autowired
//	Repositorio repo1;
//	@Autowired
//	Repositorio repo2;
//	
//	//El valor se toma del fichero application.properties, es como una variable global a la que se accede mediante @Value, de modo que si se cambia en un sitio se cambia en todos donde esté referenciada
//	@Value("${mi.valor:valor por defecto}")
//	String valor;
	

//	@Override
//	public void run(String... args) throws Exception {
//		// TODO Auto-generated method stub
//		System.err.println("Aplicacion arrancada");
//		
//		ejemplosPruebas();
//	}
	
	
//	private void ejemplosIOC() {
//		//Servicio srv = new Servicio(new Repositorio(new Configuracion()));
//
//				//AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
//				//srv.guardar();
//				repo1.guardar();
//				repo2.guardar();
//				System.err.println("Valor: " + valor);
//				System.err.println("Rango: " + rango);
//	}


		
	
	
	
	
	
//	//Metodo que ejecuta acciones despues de que finaliza el arranque de la aplicación
//	@Bean
//	public CommandLineRunner demo()
//	{
//		return (args) -> {
//			System.err.println("Aplicacion Arrancadaaaaaa");
//		};
//	}
}
