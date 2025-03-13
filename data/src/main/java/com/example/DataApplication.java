package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.CommandLineRunner;

import com.example.domains.contracts.repositories.ActorRepository;
import com.example.domains.contracts.services.ActoresService;


@SpringBootApplication
public class DataApplication implements CommandLineRunner {

	//Ahora solo se puede usar el repositorio desde el servicio
	// @Autowired
	// private ActorRepository dao;

	@Autowired
	private ActoresService srv;

	public static void main(String[] args) {
		SpringApplication.run(DataApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
		System.out.println("Aplicacion Iniciada");
		// ejemplosDatos();
		ejemplosConsultas();
	}



	private void ejemplosConsultas(){
		// dao.findTop5ByFirstNameStartingWithOrderByLastNameDesc("P").forEach(System.err::println);
		// dao.findTop5ByFirstNameStartingWith("P", Sort.by("lastName")).forEach(System.err::println);
		// dao.findByActorIdGreaterThan(100).forEach(System.err::println);
		// dao.findNovedadesJPQL(100).forEach(System.err::println);
		// dao.findNovedadesSQL(100).forEach(System.err::println);
		// dao.findAll((root, query, builder) -> builder.lessThanOrEqualTo(root.get("actorId"), 5)).forEach(System.err::println);

		// srv.getAll().forEach(System.err::println);
		srv.GreaterThanId(190);
	}


	// private void ejemplosDatos(){
	// 	//Le ponemos como clave primaria 0 para que no haya problemas con la base de datos al insertar y lo cree automaticamente
	// 	// var actor = new Actor(0, "Pepito", "Grillo", null);
	// 	// dao.save(actor);
	// 	var item = dao.findById(201);
	// 	//Para asegurar de no mandar un null
	// 	if(item.isPresent()){
	// 		//Extraccion del actor para trabajar con el
	// 		var actor = item.get();
	// 		actor.setFirstName("VINICIUS");
	// 		actor.setLastName(actor.getLastName().toUpperCase());
	// 		dao.save(actor);
	// 	} else {
	// 		System.err.println("No se ha encontrado el actor");
	// 	}
	// 	dao.findAll().forEach(System.err::println);
	// 	dao.deleteById(201);
	// 	System.err.println("Borrado");
	// 	dao.findAll().forEach(System.err::println);
	// }

	

}
