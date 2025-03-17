package com.example.entities;

import static org.junit.jupiter.api.Assertions.*;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import jakarta.validation.Validation;
import com.example.domains.entities.Language;

import java.util.Set;

class LanguageTest {

    private Language language;
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
}

