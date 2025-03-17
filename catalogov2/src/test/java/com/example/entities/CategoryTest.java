package com.example.entities;

import static org.junit.jupiter.api.Assertions.*;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import jakarta.validation.Validation;

import java.util.List;
import java.util.Set;

import com.example.domains.entities.Category;
import com.example.domains.entities.FilmCategory;


class CategoryTest {

    private Category category;
    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }


    @Test
    void testCategoryWithInvalidName() {
        Category category = new Category();
        category.setName("");

        Set<ConstraintViolation<Category>> violations = validator.validate(category);

        assertFalse(violations.isEmpty(), "Debe haber errores de validación");

        assertEquals(2, violations.size());
    }

    @Test
    void testCategoryWithValidName() {
        Category category = new Category();
        category.setName("Action");

        Set<ConstraintViolation<Category>> violations = validator.validate(category);

        assertTrue(violations.isEmpty(), "La categoría debe ser válida");
    }

    @Test
    void testCategoryWithNullName() {
        Category category = new Category();
        category.setName(null);

        Set<ConstraintViolation<Category>> violations = validator.validate(category);

        assertFalse(violations.isEmpty(), "Debe haber errores de validación");

        assertEquals(1, violations.size());
    }
}

