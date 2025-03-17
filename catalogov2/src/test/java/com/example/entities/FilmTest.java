package com.example.entities;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.Validation;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.example.domains.entities.Film;
import com.example.domains.entities.Language;
import com.example.domains.entities.FilmActor;
import com.example.domains.entities.FilmCategory;

import java.util.Set;

class FilmTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testFilmWithValidTitle() {
        Film film = new Film();
        film.setTitle("Inception");

        Set<ConstraintViolation<Film>> violations = validator.validate(film);

        assertTrue(violations.isEmpty(), "La película debe ser válida");
    }

    @Test
    void testFilmWithEmptyTitle() {
        Film film = new Film();
        film.setTitle("");

        Set<ConstraintViolation<Film>> violations = validator.validate(film);

        assertFalse(violations.isEmpty(), "Debe haber errores de validación");

        assertEquals(2, violations.size());
    }

    @Test
    void testFilmWithTooLongTitle() {
        Film film = new Film();
        film.setTitle("\"Este es un ejemplo de un mensaje que tiene exactamente 130 caracteres, superando el límite de 128 caracteres, lo cual debe generar un error de validación.");

        Set<ConstraintViolation<Film>> violations = validator.validate(film);

        assertFalse(violations.isEmpty(), "Debe haber errores de validación");

        assertEquals(1, violations.size());
        assertEquals("size must be between 2 and 128", violations.iterator().next().getMessage());
    }

    @Test
    void testFilmWithNullTitle() {
        Film film = new Film();
        film.setTitle(null);

        Set<ConstraintViolation<Film>> violations = validator.validate(film);

        assertFalse(violations.isEmpty(), "Debe haber errores de validación");

        assertEquals(1, violations.size());
    }


    @Test
    void testFilmAddRemoveFilmActor() {
        Film film = new Film();
        film.setTitle("Inception");

        // Inicializar las listas
        film.setFilmActors(new ArrayList<>());

        FilmActor actor = new FilmActor();
        film.addFilmActor(actor);

        assertEquals(1, film.getFilmActors().size(), "El actor debe ser añadido a la lista de actores.");
        assertEquals(film, actor.getFilm(), "El actor debe estar asociado a la película.");

        film.removeFilmActor(actor);

        assertEquals(0, film.getFilmActors().size(), "El actor debe ser eliminado de la lista.");
        assertNull(actor.getFilm(), "El actor debe ser desasociado de la película.");
    }

    @Test
    void testFilmAddRemoveFilmCategory() {
        Film film = new Film();
        film.setTitle("Inception");

        // Inicializar las listas
        film.setFilmCategories(new ArrayList<>());

        FilmCategory category = new FilmCategory();
        film.addFilmCategory(category);

        assertEquals(1, film.getFilmCategories().size(), "La categoría debe ser añadida a la lista de categorías.");
        assertEquals(film, category.getFilm(), "La categoría debe estar asociada a la película.");

        film.removeFilmCategory(category);

        assertEquals(0, film.getFilmCategories().size(), "La categoría debe ser eliminada de la lista.");
        assertNull(category.getFilm(), "La categoría debe ser desasociada de la película.");
    }

    @Test
    void testFilmToString() {
        Film film = new Film();
        film.setTitle("Inception");

        String expectedString = "PELICULA: TITULO Inception";
        assertEquals(expectedString, film.toString(), "El método toString() debe devolver el formato correcto.");
    }

    @Test
    void testFilmWithAllFields() {
        Film film = new Film();
        film.setTitle("Inception");
        film.setDescription("A mind-bending thriller.");
        film.setRentalRate(BigDecimal.valueOf(3.99));
        film.setReplacementCost(BigDecimal.valueOf(10.99));
        film.setLength(148);
        film.setReleaseYear((short) 2010);
        film.setRentalDuration((byte) 7);
        film.setRating("PG-13");

        // Asumimos que Language se ha inicializado previamente
        Language language = new Language();
        language.setName("English");
        film.setLanguage(language);

        Set<ConstraintViolation<Film>> violations = validator.validate(film);

        assertTrue(violations.isEmpty(), "La película con todos los campos debe ser válida.");
    }
}
