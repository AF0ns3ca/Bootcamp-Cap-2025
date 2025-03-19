package com.example.entities;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import jakarta.validation.*;

import java.math.BigDecimal;
import java.util.Set;

import com.example.domains.entities.Film;
import com.example.domains.entities.Language;
import com.example.domains.entities.Film.SpecialFeature;
import com.example.domains.entities.Actor;
import com.example.domains.entities.FilmActor;
import com.example.domains.entities.FilmCategory;
import com.example.domains.entities.Category;

import static org.mockito.Mockito.*;
import org.mockito.*;
import java.util.*;

class FilmTest {

    @Mock
    private Language language;
    @Mock
    private Language languageVO;
    @Mock
    private Actor actor;
    @Mock
    private Category category;

    private Film film;
    private Validator validator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        film = new Film("Film Title", language, (byte)5, BigDecimal.valueOf(4.99), BigDecimal.valueOf(10.99));
        
        // Initializing the Validator for bean validation
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testRemoveActor() {
        film.addActor(actor);
        film.removeActor(actor);
        assertTrue(film.getActors().isEmpty());
    }

    @Test
    void testAddCategory() {
        film.addCategory(category);
        assertFalse(film.getCategories().isEmpty());
    }

    @Test
    void testRemoveCategory() {
        film.addCategory(category);
        film.removeCategory(category);
        assertTrue(film.getCategories().isEmpty());
    }

    @Test
    void testMerge() {
        Film targetFilm = new Film();
        targetFilm = film.merge(targetFilm);

        assertEquals(film.getTitle(), targetFilm.getTitle());
        assertEquals(film.getRentalRate(), targetFilm.getRentalRate());
    }

    @Test
    void testSetTitle() {
        film.setTitle("New Title");
        assertEquals("New Title", film.getTitle());
    }

    @Test
    void testSetDescription() {
        film.setDescription("New description");
        assertEquals("New description", film.getDescription());
    }

    @Test
    void testSetRentalRate() {
        film.setRentalRate(BigDecimal.valueOf(6.99));
        assertEquals(BigDecimal.valueOf(6.99), film.getRentalRate());
    }

    @Test
    void testSetReplacementCost() {
        film.setReplacementCost(BigDecimal.valueOf(15.99));
        assertEquals(BigDecimal.valueOf(15.99), film.getReplacementCost());
    }

    @Test
    void testAddSpecialFeatures() {
        film.addSpecialFeatures(SpecialFeature.Trailers);
        assertTrue(film.getSpecialFeatures().contains(SpecialFeature.Trailers));
    }

    @Test
    void testRemoveSpecialFeatures() {
        film.addSpecialFeatures(SpecialFeature.Trailers);
        film.removeSpecialFeatures(SpecialFeature.Trailers);
        assertFalse(film.getSpecialFeatures().contains(SpecialFeature.Trailers));
    }

    @Test
    void testEqualsAndHashCode() {
        Film anotherFilm = new Film(film.getFilmId());
        assertTrue(film.equals(anotherFilm));
        assertEquals(film.hashCode(), anotherFilm.hashCode());
    }

    // Test for validation: Film with invalid title (empty)
    @Test
    void testInvalidFilmTitle() {
        film.setTitle("");
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(violation -> violation.getPropertyPath().toString().equals("title")));
    }

    // Test for validation: Film with invalid rentalRate (negative value)
    @Test
    void testInvalidRentalRate() {
        film.setRentalRate(BigDecimal.valueOf(-4.99));
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(violation -> violation.getPropertyPath().toString().equals("rentalRate")));
    }

    // Test for validation: Film with invalid replacementCost (negative value)
    @Test
    void testInvalidReplacementCost() {
        film.setReplacementCost(BigDecimal.valueOf(-10.99));
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(violation -> violation.getPropertyPath().toString().equals("replacementCost")));
    }

    // Test for validation: Film with null language
    @Test
    void testNullLanguage() {
        film.setLanguage(null);
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(violation -> violation.getPropertyPath().toString().equals("language")));
    }

    // Test for validation: Film with rentalDuration <= 0 (invalid rental duration)
    @Test
    void testInvalidRentalDuration() {
        film.setRentalDuration((byte) 0);
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(violation -> violation.getPropertyPath().toString().equals("rentalDuration")));
    }

    // Test for valid Film object
    @Test
    void testValidFilm() {
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertTrue(violations.isEmpty(), "Film should be valid");
    }
}
