package com.example.services;

import com.example.domains.contracts.repositories.ActorRepository;
import com.example.domains.services.ActoresServiceImpl;
import com.example.domains.entities.Actor;
import com.example.exceptions.DuplicateKeyException;
import com.example.exceptions.InvalidDataException;
import com.example.exceptions.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.Mockito.times;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ActoresServiceImplTest {

    @Mock
    private ActorRepository actorRepository;

    @InjectMocks
    private ActoresServiceImpl actoresService;

    private Actor validActor;

    @BeforeEach
    void setUp() {
        validActor = new Actor(1, "John", "Doe");
    }

    @Test
    void testGetAll() {
        List<Actor> actors = List.of(new Actor(1, "John", "Doe"), new Actor(2, "Jane", "Smith"));

        when(actorRepository.findAll()).thenReturn(actors);

        List<Actor> result = actoresService.getAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("John", result.get(0).getFirstName());
        assertEquals("Jane", result.get(1).getFirstName());
    }

    @Test
    void testGetOneActorFound() {
        Actor actor = new Actor(1, "John", "Doe");
        when(actorRepository.findById(1)).thenReturn(Optional.of(actor));
        Optional<Actor> result = actoresService.getOne(1);
        assertTrue(result.isPresent());
        assertEquals(1, result.get().getActorId());
        assertEquals("John", result.get().getFirstName());
    }

    @Test
    void testGetOneActorNotFound() {
        when(actorRepository.findById(99)).thenReturn(Optional.empty());
        Optional<Actor> result = actoresService.getOne(99);
        assertFalse(result.isPresent());
    }

    @Test
    void testAddActor() throws DuplicateKeyException, InvalidDataException {
        validActor = new Actor(1, "John", "Doe");

        when(actorRepository.existsById(validActor.getActorId())).thenReturn(false);
        when(actorRepository.save(validActor)).thenReturn(validActor);

        Actor addedActor = actoresService.add(validActor);

        assertNotNull(addedActor);
        assertEquals(validActor.getActorId(), addedActor.getActorId());
        verify(actorRepository, times(1)).save(validActor);
    }

    @Test
    void testAddActorWithDuplicateId() {
        when(actorRepository.existsById(validActor.getActorId())).thenReturn(true);

        DuplicateKeyException exception = assertThrows(DuplicateKeyException.class, () -> {
            actoresService.add(validActor);
        });
        assertEquals("Ya existe un actor con ese id", exception.getMessage());
    }

    @Test
    void testAddActorWithInvalidData() {
        Actor invalidActor = new Actor(0, null, "");

        InvalidDataException exception = assertThrows(InvalidDataException.class, () -> {
            actoresService.add(invalidActor);
        });
        assertEquals("No se puede añadir un valor nulo", exception.getMessage());
    }

    @Test
    void testModifyActor() throws InvalidDataException, NotFoundException {
        validActor = new Actor(1, "John", "Doe");

        when(actorRepository.existsById(validActor.getActorId())).thenReturn(true);
        when(actorRepository.save(validActor)).thenReturn(validActor);

        Actor modifiedActor = actoresService.modify(validActor);

        assertNotNull(modifiedActor);
        assertEquals(validActor.getActorId(), modifiedActor.getActorId());
        verify(actorRepository, times(1)).save(validActor);
    }

    @Test
    void testModifyActorNotFound() {
        validActor = new Actor(1, "John", "Doe");

        when(actorRepository.existsById(validActor.getActorId())).thenReturn(false);

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            actoresService.modify(validActor);
        });

        assertEquals("No existe un actor con ese id", exception.getMessage());
    }

    @Test
    void testDeleteActor() throws InvalidDataException {
        actoresService.delete(validActor);

        verify(actorRepository, times(1)).delete(validActor);
    }

    @Test
    void testDeleteActorWithInvalidData() {
        Actor invalidActor = new Actor(-1, null, null);

        InvalidDataException exception = assertThrows(InvalidDataException.class, () -> {
            actoresService.delete(invalidActor);
        });
        assertEquals("No se puede añadir un valor nulo", exception.getMessage());
    }

    @Test
    void testDeleteActorWithInvalidId() {
        Actor invalidActor = new Actor(-1, "PEDRO", "PICAPIEDRA");

        InvalidDataException exception = assertThrows(InvalidDataException.class, () -> {
            actoresService.delete(invalidActor);
        });
        assertEquals("No se puede añadir un valor nulo", exception.getMessage());
    }
    @Test
    void testDeleteActorWithInvalidFirstName() {
        Actor invalidActor = new Actor(0, null, "PICAPIEDRA");

        InvalidDataException exception = assertThrows(InvalidDataException.class, () -> {
            actoresService.delete(invalidActor);
        });
        assertEquals("No se puede añadir un valor nulo", exception.getMessage());
    }

    @Test
    void testDeleteActorWithInvalidLastName() {
        Actor invalidActor = new Actor(0, "PEDRO", null);

        InvalidDataException exception = assertThrows(InvalidDataException.class, () -> {
            actoresService.delete(invalidActor);
        });
        assertEquals("No se puede añadir un valor nulo", exception.getMessage());
    }

    @Test
    void testDeleteById() {
        actoresService.deleteById(validActor.getActorId());

        verify(actorRepository, times(1)).deleteById(validActor.getActorId());
    }

    @Test
    void testGreaterThanIdWithNoResults() {
        when(actorRepository.findByActorIdGreaterThan(1)).thenReturn(List.of());

        actoresService.GreaterThanId(1);

        verify(actorRepository, times(1)).findByActorIdGreaterThan(1);
    }

    @Test
    void testGreaterThanIdWithResults() {
        Actor actor = new Actor(2, "Jane", "Smith");
        when(actorRepository.findByActorIdGreaterThan(1)).thenReturn(List.of(actor));

        actoresService.GreaterThanId(1);

        verify(actorRepository, times(1)).findByActorIdGreaterThan(1);
    }

    @Test
    void testAddActorWithNullFirstName() {
        Actor actorWithNullName = new Actor(1, null, "Doe");

        InvalidDataException exception = assertThrows(InvalidDataException.class, () -> {
            actoresService.add(actorWithNullName);
        });
        assertEquals("No se puede añadir un valor nulo", exception.getMessage());
    }

    @Test
    void testModifyActorWithInvalidActorId() {
        Actor invalidActor = new Actor(-1, "John", "Doe");

        InvalidDataException exception = assertThrows(InvalidDataException.class, () -> {
            actoresService.modify(invalidActor);
        });
        assertEquals("No se puede añadir un valor nulo", exception.getMessage());
    }
}
