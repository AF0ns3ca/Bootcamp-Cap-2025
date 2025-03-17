package com.example.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.domains.entities.Actor;
import com.example.domains.entities.FilmActor;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ValidatorFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

import static org.junit.jupiter.api.Assertions.*;

class ActorTest {

    private Actor actor;
    private Validator validator;

    @BeforeEach
    void setUp() {
        actor = new Actor(1, "John", "Doe");
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testValidActor() {
        Actor actor = new Actor(1, "JOHN", "DOE");

        Set<ConstraintViolation<Actor>> violations = validator.validate(actor);

        assertTrue(violations.isEmpty(), "Debe ser válido");
    }

    @Test
    void testActorWithInvalidFirstName() {
        Actor actor = new Actor(1, "john", "Doe");

        Set<ConstraintViolation<Actor>> violations = validator.validate(actor);

        assertFalse(violations.isEmpty(), "Debe haber errores de validación");

        assertEquals(1, violations.size());
        assertEquals("El nombre debe estar en mayusculas, sin espacios y sin caracteres especiales", violations.iterator().next().getMessage());
    }

    @Test
    void testActorWithBlankFirstName() {
        Actor actor = new Actor(1, "", "Doe");

        Set<ConstraintViolation<Actor>> violations = validator.validate(actor);

        assertFalse(violations.isEmpty(), "Debe haber errores de validación");

        assertEquals(2, violations.size());
    }

    @Test
    void testActorWithInvalidLastName() {
        Actor actor = new Actor(1, "John", "D");

        Set<ConstraintViolation<Actor>> violations = validator.validate(actor);

        assertFalse(violations.isEmpty(), "Debe haber errores de validación");

        assertEquals(2, violations.size());
    }

    @Test
    void testActorWithNullLastName() {
        Actor actor = new Actor(1, "John", null);

        Set<ConstraintViolation<Actor>> violations = validator.validate(actor);

        assertFalse(violations.isEmpty(), "Debe haber errores de validación");

        assertEquals(2, violations.size());
    }

    @Test
    void testActorCreation() {
        assertNotNull(actor);
        assertEquals(1, actor.getActorId());
        assertEquals("John", actor.getFirstName());
        assertEquals("Doe", actor.getLastName());
    }

    @Test
    void testSetFirstNameWithNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            actor.setFirstName(null);
        });
        assertEquals("El nombre no puede ser nulo", exception.getMessage());
    }

    @Test
    void setFirstNameValid() {
        actor.setFirstName("John");
        assertEquals("John", actor.getFirstName());
    }

    @Test
    void testSetLastNameValid() {
        actor.setLastName("Doe");
        assertEquals("Doe", actor.getLastName());
    }

    @Test
    void testSetFilmActors() {

        FilmActor filmActor1 = new FilmActor();
        FilmActor filmActor2 = new FilmActor();
        List<FilmActor> filmActorsList = List.of(filmActor1, filmActor2);

        actor.setFilmActors(filmActorsList);

        assertNotNull(actor.getFilmActors());
        assertEquals(2, actor.getFilmActors().size());
        assertTrue(actor.getFilmActors().contains(filmActor1));
        assertTrue(actor.getFilmActors().contains(filmActor2));
    }

    @Test
    void testAddFilmActor() {

        FilmActor filmActor = new FilmActor();

        actor.setFilmActors(new ArrayList<>());

        FilmActor addedFilmActor = actor.addFilmActor(filmActor);

        assertNotNull(actor.getFilmActors());
        assertEquals(1, actor.getFilmActors().size());
        assertTrue(actor.getFilmActors().contains(filmActor));
        assertEquals(actor, filmActor.getActor());
        assertSame(filmActor, addedFilmActor);
    }

    @Test
    void testRemoveFilmActor() {

        FilmActor filmActor = new FilmActor();

        actor.setFilmActors(new ArrayList<>());
        actor.addFilmActor(filmActor);

        FilmActor removedFilmActor = actor.removeFilmActor(filmActor);

        assertFalse(actor.getFilmActors().contains(filmActor));
        assertEquals(0, actor.getFilmActors().size());

        assertNull(filmActor.getActor());

        assertSame(filmActor, removedFilmActor);
    }

    @Test
    void testEqualsAndHashCode() {
        Actor actor1 = new Actor(1, "John", "Doe");
        Actor actor2 = new Actor(1, "John", "Doe");

        assertTrue(actor1.equals(actor2));
        assertEquals(actor1.hashCode(), actor2.hashCode());
    }

    @Test
    void testToString() {
        String expected = "Actor [actorId=1, firstName=John, lastName=Doe, lastUpdate=null]";
        assertEquals(expected, actor.toString());
    }

    @Test
    void testJubilate() {
        actor.jubilate();
    }

    @Test
    void testPremioRecibido() {
        actor.premioRecibido();
    }
}
