package com.example.entities;

import static org.junit.jupiter.api.Assertions.*;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import jakarta.validation.Validation;
import com.example.domains.entities.Film;

import java.util.Set;

class FilmTest {

    private Film film;
    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testFilmWithValidTitle() {
        Film film = new Film();
        film.setTitle("The Great Adventure");

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
        assertEquals("size must be between 2 and 128", violations.iterator().next().getMessage());
    }

    @Test
    void testFilmWithTooShortTitle() {
        Film film = new Film();
        film.setTitle("A");

        Set<ConstraintViolation<Film>> violations = validator.validate(film);

        assertFalse(violations.isEmpty(), "Debe haber errores de validación");
        assertEquals(1, violations.size());
        assertEquals("size must be between 2 and 128", violations.iterator().next().getMessage());
    }

    @Test
    void testFilmWithTooLongTitle() {
        Film film = new Film();
        film.setTitle("A Very Long Film Title That Exceeds The Maximum Length Of The Allowed Title Field In The Database But It Is Still Acceptable For Testing Purposes");

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
}
