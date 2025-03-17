package com.example.entities;

import static org.junit.jupiter.api.Assertions.*;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import jakarta.validation.Validation;
import com.example.domains.entities.Language;
import com.example.domains.entities.Film;

import java.util.Set;
import java.util.ArrayList;

class LanguageTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testLanguageWithValidName() {
        Language language = new Language();
        language.setName("English");

        Set<ConstraintViolation<Language>> violations = validator.validate(language);

        assertTrue(violations.isEmpty(), "El lenguaje debe ser válido");
    }

    @Test
    void testLanguageWithEmptyName() {
        Language language = new Language();
        language.setName("");

        Set<ConstraintViolation<Language>> violations = validator.validate(language);

        assertFalse(violations.isEmpty(), "Debe haber errores de validación");

        assertEquals(2, violations.size());
    }

    @Test
    void testLanguageWithTooShortName() {
        Language language = new Language();
        language.setName("A");

        Set<ConstraintViolation<Language>> violations = validator.validate(language);

        assertFalse(violations.isEmpty(), "Debe haber errores de validación");

        assertEquals(1, violations.size());
        assertEquals("size must be between 2 and 20", violations.iterator().next().getMessage());
    }

    @Test
    void testLanguageWithTooLongName() {
        Language language = new Language();
        language.setName("A very long language name that exceeds the limit");

        Set<ConstraintViolation<Language>> violations = validator.validate(language);

        assertFalse(violations.isEmpty(), "Debe haber errores de validación");

        assertEquals(1, violations.size());
        assertEquals("size must be between 2 and 20", violations.iterator().next().getMessage());
    }

    @Test
    void testLanguageWithNullName() {
        Language language = new Language();
        language.setName(null);

        Set<ConstraintViolation<Language>> violations = validator.validate(language);

        assertFalse(violations.isEmpty(), "Debe haber errores de validación");

        assertEquals(1, violations.size());
    }


    @Test
    void testAddFilmToLanguage() {
        Language language = new Language();
        language.setName("English");

        // Inicializar las listas de films
        language.setFilms(new ArrayList<>());
        
        Film film = new Film();
        language.addFilm(film);

        assertEquals(1, language.getFilms().size(), "La película debe ser añadida a la lista de films.");
        assertEquals(language, film.getLanguage(), "La relación de la película debe estar correctamente asignada.");
    }

    @Test
    void testRemoveFilmFromLanguage() {
        Language language = new Language();
        language.setName("English");

        language.setFilms(new ArrayList<>());
        
        Film film = new Film();
        language.addFilm(film);

        language.removeFilm(film);

        assertEquals(0, language.getFilms().size(), "La película debe ser eliminada de la lista de films.");
        assertNull(film.getLanguage(), "La relación de la película debe ser eliminada.");
    }

    @Test
    void testAddFilmVOToLanguage() {
        Language language = new Language();
        language.setName("English");

        language.setFilmsVO(new ArrayList<>());
        
        Film film = new Film();
        language.addFilmsVO(film);

        assertEquals(1, language.getFilmsVO().size(), "La película VO debe ser añadida a la lista de filmsVO.");
        assertEquals(language, film.getLanguageVO(), "La relación de la película VO debe estar correctamente asignada.");
    }

    @Test
    void testRemoveFilmVOFromLanguage() {
        Language language = new Language();
        language.setName("English");

        language.setFilmsVO(new ArrayList<>());
        
        Film film = new Film();
        language.addFilmsVO(film);

        language.removeFilmsVO(film);

        assertEquals(0, language.getFilmsVO().size(), "La película VO debe ser eliminada de la lista de filmsVO.");
        assertNull(film.getLanguageVO(), "La relación de la película VO debe ser eliminada.");
    }
}
