package com.example.entities;

import static org.junit.jupiter.api.Assertions.*;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import jakarta.validation.Validation;

import java.sql.Timestamp;
import java.util.Set;

import com.example.domains.entities.Category;

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
        category.setName(""); // Nombre vacío

        Set<ConstraintViolation<Category>> violations = validator.validate(category);

        assertFalse(violations.isEmpty(), "Debe haber errores de validación");
        assertEquals(2, violations.size(), "Debe haber un error por el nombre vacío");
    }

    @Test
    void testCategoryWithValidName() {
        category.setName("Action"); // Nombre válido

        Set<ConstraintViolation<Category>> violations = validator.validate(category);

        assertTrue(violations.isEmpty(), "La categoría debe ser válida");
    }

    @Test
    void testCategoryWithNullName() {
        category.setName(null); // Nombre nulo

        Set<ConstraintViolation<Category>> violations = validator.validate(category);

        assertFalse(violations.isEmpty(), "Debe haber errores de validación");
        assertEquals(1, violations.size(), "Debe haber un error por el nombre nulo");
    }

    @Test
    void testCategoryId() {
        category.setCategoryId(1);
        assertEquals(1, category.getCategoryId(), "El ID de la categoría debe ser 1");
    }

    @Test
    void testName() {
        category.setName("Action");
        assertEquals("Action", category.getName(), "El nombre de la categoría debe ser 'Action'");
    }

    @Test
    void testLastUpdate() {
        Timestamp timestamp = Timestamp.valueOf("2025-03-17 10:10:10");
        category.setLastUpdate(timestamp);
        assertEquals(timestamp, category.getLastUpdate(), "La última actualización debe ser la correcta");
    }

    @Test
    void testValidCategoryName() {
        category.setName("Action");
        Set<ConstraintViolation<Category>> violations = validator.validate(category);
        assertTrue(violations.isEmpty(), "El nombre debe ser válido.");
    }

    @Test
    void testCategoryNameNotBlankValidation() {
        category.setName("");
        Set<ConstraintViolation<Category>> violations = validator.validate(category);
        assertFalse(violations.isEmpty(), "El nombre no debe estar vacío.");
        
        category.setName("  "); // Espacios en blanco
        violations = validator.validate(category);
        assertFalse(violations.isEmpty(), "El nombre no debe estar vacío.");
    }

    @Test
    void testCategoryNameSizeValidation() {
        category.setName("A"); // Nombre demasiado corto
        Set<ConstraintViolation<Category>> violations = validator.validate(category);
        assertFalse(violations.isEmpty(), "El nombre debe tener al menos 2 caracteres.");
        
        category.setName("A very long category name that exceeds the limit"); // Nombre demasiado largo
        violations = validator.validate(category);
        assertFalse(violations.isEmpty(), "El nombre no debe exceder los 25 caracteres.");
        
        category.setName("Action"); // Nombre válido
        violations = validator.validate(category);
        assertTrue(violations.isEmpty(), "El nombre debe ser válido.");
    }

    @Test
    void testToString() {
        category.setName("Action");
        category.setCategoryId(1);
        String result = category.toString();
        assertTrue(result.contains("Action"), "toString debe incluir el nombre de la categoría.");
        assertTrue(result.contains("categoryId=" + 1), "toString debe incluir el ID de la categoría.");
    }

    // Test para hashCode
    @Test
    void testHashCode() {
        Category category1 = new Category(1, "Action");
        Category category2 = new Category(1, "Action"); // Mismo ID y nombre que category1
        Category category3 = new Category(2, "Comedy"); // ID diferente de category1

        // Verificamos que dos categorías con el mismo ID tengan el mismo hashCode
        assertEquals(category1.hashCode(), category2.hashCode(), "Los hashCode deben ser iguales para categorías con el mismo ID");
        
        // Verificamos que categorías con IDs diferentes tengan hashCode diferentes
        assertNotEquals(category1.hashCode(), category3.hashCode(), "Los hashCode deben ser diferentes para categorías con IDs diferentes");
    }

    // Test para equals
    @Test
    void testEquals() {
        Category category1 = new Category(1, "Action");
        Category category2 = new Category(1, "Action");  // Mismo ID y nombre que category1
        Category category3 = new Category(2, "Comedy");  // ID diferente de category1

        // Verificamos que una categoría sea igual a sí misma
        assertTrue(category1.equals(category1), "Una categoría debe ser igual a sí misma");

        // Verificamos que dos categorías con el mismo ID y nombre sean iguales
        assertTrue(category1.equals(category2), "Las categorías con el mismo ID y nombre deben ser iguales");

        // Verificamos que dos categorías con diferentes IDs no sean iguales
        assertFalse(category1.equals(category3), "Las categorías con diferentes IDs no deben ser iguales");

        // Verificamos que la comparación con un objeto de otro tipo devuelva false
        assertFalse(category1.equals(new Object()), "Una categoría no debe ser igual a un objeto de otro tipo");
    }
}
