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
        assertEquals(2, violations.size()); // Debería haber más de un error, ya que tiene restricciones de tamaño.
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

        // Inicializar la lista de films
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

    // Prueba de añadir una película VO a Language
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

    // Prueba de eliminar una película VO de Language
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

    @Test
void testHashCodeEqualLanguages() {
    Language language1 = new Language();
    language1.setLanguageId(1);
    language1.setName("English");

    Language language2 = new Language();
    language2.setLanguageId(1);
    language2.setName("English");

    // Verifica que los hashCodes de dos objetos con el mismo `languageId` sean iguales
    assertEquals(language1.hashCode(), language2.hashCode(), "Los hashCodes deben ser iguales para objetos Language con el mismo languageId");
}

@Test
void testHashCodeDifferentLanguages() {
    Language language1 = new Language();
    language1.setLanguageId(1);
    language1.setName("English");

    Language language2 = new Language();
    language2.setLanguageId(2);  // Modifica el languageId para hacerlo diferente
    language2.setName("English");

    // Verifica que los hashCodes de dos objetos con diferentes `languageId` sean diferentes
    assertNotEquals(language1.hashCode(), language2.hashCode(), "Los hashCodes deben ser diferentes para objetos Language con diferentes languageId");
}

@Test
void testEqualsSameObject() {
    Language language1 = new Language();
    language1.setLanguageId(1);
    language1.setName("English");

    // Verifica que el mismo objeto sea igual a sí mismo
    assertTrue(language1.equals(language1), "Un objeto debe ser igual a sí mismo");
}

@Test
void testEqualsEqualObjects() {
    Language language1 = new Language();
    language1.setLanguageId(1);
    language1.setName("English");

    Language language2 = new Language();
    language2.setLanguageId(1);
    language2.setName("English");

    // Verifica que dos objetos con el mismo `languageId` y `name` sean iguales
    assertTrue(language1.equals(language2), "Dos objetos con el mismo languageId y name deben ser iguales");
}

@Test
void testEqualsDifferentObjects() {
    Language language1 = new Language();
    language1.setLanguageId(1);
    language1.setName("English");

    Language language2 = new Language();
    language2.setLanguageId(2);  // Modifica el languageId para hacerlo diferente

    // Verifica que dos objetos con diferentes `languageId` no sean iguales
    assertFalse(language1.equals(language2), "Dos objetos con diferentes languageId no deben ser iguales");
}

@Test
void testEqualsNull() {
    Language language1 = new Language();
    language1.setLanguageId(1);
    language1.setName("English");

    // Verifica que un objeto no sea igual a null
    assertFalse(language1.equals(null), "Un objeto no debe ser igual a null");
}

@Test
void testEqualsDifferentClass() {
    Language language1 = new Language();
    language1.setLanguageId(1);
    language1.setName("English");

    // Verifica que un objeto de una clase diferente no sea igual
    assertFalse(language1.equals(new Object()), "Un objeto de una clase diferente no debe ser igual");
}

@Test
void testToString() {
    Language language1 = new Language();
    language1.setLanguageId(1);
    language1.setName("English");

    // Verifica que el método toString devuelva el formato correcto
    String expectedToString = "Language [languageId=1, name=English]";
    assertEquals(expectedToString, language1.toString(), "El método toString() debe devolver la cadena correcta.");
}

@Test
void testLanguageConstructorWithLanguageId() {
    // Crea un objeto Language usando el constructor con solo languageId
    Language language = new Language(1);

    // Verifica que el languageId se ha establecido correctamente
    assertEquals(1, language.getLanguageId(), "El constructor con solo languageId debe establecer correctamente el valor de languageId");

    // Verifica que el nombre sea null por defecto
    assertNull(language.getName(), "El nombre debe ser null si no se proporciona");
}

@Test
void testLanguageConstructorWithLanguageIdAndName() {
    // Crea un objeto Language usando el constructor con languageId y name
    Language language = new Language(1, "English");

    // Verifica que el languageId y el nombre se han establecido correctamente
    assertEquals(1, language.getLanguageId(), "El constructor con languageId y name debe establecer correctamente el valor de languageId");
    assertEquals("English", language.getName(), "El constructor con languageId y name debe establecer correctamente el valor de name");
}

@Test
void testLanguageConstructorWithInvalidName() {
    // Crea un objeto Language con un nombre inválido (null o vacío)
    Language language = new Language(1, "");

    // Verifica que el nombre vacío cause una violación de validación
    Set<ConstraintViolation<Language>> violations = validator.validate(language);

    // Asegúrate de que hay un error de validación
    assertFalse(violations.isEmpty(), "Debe haber un error de validación para el nombre vacío");
    assertEquals(2, violations.size(), "Debe haber exactamente un error de validación");

    // Verifica el mensaje de error de validación
    assertEquals("size must be between 2 and 20", violations.iterator().next().getMessage(), "El mensaje de validación debe ser sobre un campo vacío");
}


}
