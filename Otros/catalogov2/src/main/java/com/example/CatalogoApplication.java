package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.example.domains.services.ActoresServiceImpl;
import com.example.exceptions.DuplicateKeyException;
import com.example.exceptions.InvalidDataException;
import com.example.exceptions.NotFoundException;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.CommandLineRunner;

import com.example.domains.contracts.repositories.ActorRepository;
import com.example.domains.contracts.services.ActoresService;
import com.example.domains.contracts.services.FilmService;
import com.example.domains.contracts.services.LanguageService;
import com.example.domains.entities.Actor;
import com.example.domains.entities.Category;
import com.example.domains.entities.Film;
import com.example.domains.entities.FilmActor;
import com.example.domains.entities.Language;
import com.example.domains.entities.models.ActorDTO;
import com.example.domains.entities.models.ActorShort;
import com.example.domains.contracts.services.CategoryService;

import java.util.*;


import org.springframework.transaction.annotation.Transactional;


@SpringBootApplication
public class CatalogoApplication implements CommandLineRunner {


	@Autowired
	private ActorRepository dao;

	@Autowired
	private ActoresService srvActor;

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
		// srvFilm.FilmWithActors(1);
		// srvFilm.FilmWithCategories(1);

		srvFilm.addActorToFilm(2,202);
		srvFilm.filmWithActors(2);

		
		

	}

	public void crudActor() throws DuplicateKeyException, NotFoundException, InvalidDataException {
		Actor actor = new Actor(203, "ANOMANDER", "RAKE");
		srvActor.getAll().forEach(System.err::println);
		srvActor.add(actor);
		srvActor.getAll().forEach(System.err::println);
		srvActor.modify(new Actor(202, "ANOMANDER", "LAKE"));
		srvActor.delete(actor);
		srvActor.getAll().forEach(System.err::println);
	}

	public void crudFilm() throws DuplicateKeyException, NotFoundException, InvalidDataException {
		Film film = new Film();
		srvFilm.getAll().forEach(System.err::println);
		
	}

	public void crudCategory() throws DuplicateKeyException, NotFoundException, InvalidDataException {
		Category category = new Category(0, "FANTASIA");
		srvCategory.getAll().forEach(System.err::println);
		srvCategory.add(category);
		srvCategory.getAll().forEach(System.err::println);
		srvCategory.modify(new Category(23, "DARKFANTASY"));
		srvCategory.getAll().forEach(System.err::println);
		srvCategory.delete(new Category(23, "DARKFANTASY"));
		srvCategory.getAll().forEach(System.err::println);
	}

	public void crudLanguage() throws DuplicateKeyException, NotFoundException, InvalidDataException {
		Language language = new Language(0, null, "Esperanto");
		srvLanguage.getAll().forEach(System.err::println);
		srvLanguage.add(language);
		srvLanguage.getAll().forEach(System.err::println);
		srvLanguage.modify(new Language(6, null, "Esperanto"));
		srvLanguage.getAll().forEach(System.err::println);
		srvLanguage.delete(new Language(6, null, "Esperanto"));
		srvLanguage.getAll().forEach(System.err::println);
	}

}
