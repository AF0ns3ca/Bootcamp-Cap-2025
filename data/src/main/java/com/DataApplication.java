package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.domains.contracts.repositories.ActorRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.CommandLineRunner;

import com.domains.entities.Actor;


@SpringBootApplication
public class DataApplication implements CommandLineRunner {

	@Autowired
	private ActorRepository dao;

	public static void main(String[] args) {
		SpringApplication.run(DataApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
		System.out.println("Aplicacion Iniciada");
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

}
