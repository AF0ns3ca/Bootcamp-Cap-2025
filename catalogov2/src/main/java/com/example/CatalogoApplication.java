package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.CommandLineRunner;

import com.example.domains.contracts.services.ActoresService;
import com.example.domains.contracts.services.FilmService;
import com.example.domains.contracts.services.LanguageService;
import com.example.domains.contracts.services.CategoryService;


import org.springframework.transaction.annotation.Transactional;


@SpringBootApplication
public class CatalogoApplication implements CommandLineRunner {

	@Autowired
	private ActoresService srv;

	@Autowired
	private FilmService srvFilm;

	@Autowired
	private LanguageService srvLanguage;

	@Autowired
	private CategoryService srvCategory;

	public static void main(String[] args) {
		SpringApplication.run(CatalogoApplication.class, args);
	}
	
	@Override
	@Transactional
	public void run(String... args) throws Exception {
		System.out.println("Aplicacion Iniciada");
		consultActors();
		consultFilms();
		consultCategory();
		consultLanguage();
	}

	public void consultActors(){
		System.err.println("Actores");
		// srv.getAll().forEach(System.err::println);
		srv.getOne(1).ifPresent(System.err::println);
	}

	public void consultFilms(){
		System.err.println("Films");
		// srvFilm.getAll().forEach(System.err::println);
		srvFilm.getOne(1).ifPresent(System.err::println);
	}

	public void consultCategory(){
		System.err.println("Category");
		// srvLanguage.getAll().forEach(System.err::println);
		srvLanguage.getOne(1).ifPresent(System.err::println);
	}

	public void consultLanguage(){
		System.err.println("Language");
		// srvCategory.getAll().forEach(System.err::println);
		srvCategory.getOne(1).ifPresent(System.err::println);
	}

}
