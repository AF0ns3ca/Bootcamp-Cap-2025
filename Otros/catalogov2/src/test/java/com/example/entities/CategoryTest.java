package com.example.entities;

import static org.junit.jupiter.api.Assertions.*;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import jakarta.validation.Validation;

import java.util.Set;

import com.example.domains.entities.Category;
import java.sql.Timestamp;


class CategoryTest {

    private Category category;
    private Validator validator;

    @BeforeEach
    void setUp() {
        category = new Category();
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

    @Test
    void testCategoryId() {
        category.setCategoryId(1);
        assertEquals(1, category.getCategoryId());
    }

    @Test
    void testName() {
        category.setName("Action");
        assertEquals("Action", category.getName());
    }

    @Test
    void testLastUpdate() {
        Timestamp timestamp = Timestamp.valueOf("2025-03-17 10:10:10");
        category.setLastUpdate(timestamp);
        assertEquals(timestamp, category.getLastUpdate());
    }

    @Test
    void testValidCategoryName() {
        category.setName("Action");
        Set<ConstraintViolation<Category>> violations = validator.validate(category);
        assertTrue(violations.isEmpty(), "The name should be valid.");
    }

    @Test
    void testCategoryNameNotBlankValidation() {
        category.setName("");
        Set<ConstraintViolation<Category>> violations = validator.validate(category);
        assertFalse(violations.isEmpty(), "The name should not be blank.");
        
        category.setName("  ");
        violations = validator.validate(category);
        assertFalse(violations.isEmpty(), "The name should not be blank.");
    }

    @Test
    void testCategoryNameSizeValidation() {
        category.setName("A");
        Set<ConstraintViolation<Category>> violations = validator.validate(category);
        assertFalse(violations.isEmpty(), "The name should be at least 2 characters.");
        
        category.setName("A very long category name that exceeds the limit");
        violations = validator.validate(category);
        assertFalse(violations.isEmpty(), "The name should be no more than 25 characters.");
        
        category.setName("Action");
        violations = validator.validate(category);
        assertTrue(violations.isEmpty(), "The name should be valid.");
    }


    @Test
    void testToString() {
        category.setName("Action");
        category.setCategoryId(1);
        String result = category.toString();
        assertTrue(result.contains("Action"), "toString should include the category name.");
        assertTrue(result.contains("ID=1"), "toString should include the category ID.");
    }
}

