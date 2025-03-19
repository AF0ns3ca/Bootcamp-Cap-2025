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
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Sort;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.extension.ExtendWith;

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

    // Test para obtener todos los actores sin orden
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

    // Test para obtener todos los actores con orden
    @Test
    void testGetAllWithSort() {
        List<Actor> actors = List.of(new Actor(1, "John", "Doe"), new Actor(2, "Jane", "Smith"));
        when(actorRepository.findAll(any(Sort.class))).thenReturn(actors);

        Iterable<Actor> result = actoresService.getAll(Sort.by("firstName"));

        assertNotNull(result);
        assertEquals(2, ((List<Actor>) result).size());
    }

    // Test para obtener un actor por ID cuando se encuentra
    @Test
    void testGetOneActorFound() {
        Actor actor = new Actor(1, "John", "Doe");
        when(actorRepository.findById(1)).thenReturn(Optional.of(actor));

        Optional<Actor> result = actoresService.getOne(1);

        assertTrue(result.isPresent());
        assertEquals(1, result.get().getActorId());
        assertEquals("John", result.get().getFirstName());
    }

    // Test para obtener un actor por ID cuando no se encuentra
    @Test
    void testGetOneActorNotFound() {
        when(actorRepository.findById(99)).thenReturn(Optional.empty());

        Optional<Actor> result = actoresService.getOne(99);

        assertFalse(result.isPresent());
    }

    // Test para agregar un actor exitosamente
    @Test
    void testAddActor() throws DuplicateKeyException, InvalidDataException {
        validActor = new Actor(1, "JOHN", "DOE");
    
        when(actorRepository.insert(validActor)).thenReturn(validActor);
    
        Actor addedActor = actoresService.add(validActor);
    
        assertNotNull(addedActor);
        assertEquals(validActor.getActorId(), addedActor.getActorId());
        verify(actorRepository, times(1)).insert(validActor);
    }
    

    // Test para agregar un actor con datos inválidos
    @Test
    void testAddActorWithInvalidData() {
        Actor invalidActor = new Actor(0, null, "");

        InvalidDataException exception = assertThrows(InvalidDataException.class, () -> {
            actoresService.add(invalidActor);
        });

        assertEquals("El nombre no puede ser nulo", exception.getMessage());
    }

    // Test para modificar un actor exitosamente
    @Test
    void testModifyActor() throws InvalidDataException, NotFoundException {
        validActor = new Actor(1, "JOHN", "DOE");

        when(actorRepository.existsById(validActor.getActorId())).thenReturn(true);
        when(actorRepository.update(validActor)).thenReturn(validActor);

        Actor modifiedActor = actoresService.modify(validActor);

        assertNotNull(modifiedActor);
        assertEquals(validActor.getActorId(), modifiedActor.getActorId());
        verify(actorRepository, times(1)).update(validActor);
    }

    // Test para modificar un actor cuando no se encuentra
    @Test
    void testModifyActorNotFound() {
        validActor = new Actor(1, "John", "Doe");

        when(actorRepository.existsById(validActor.getActorId())).thenReturn(false);

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            actoresService.modify(validActor);
        });

        assertEquals("No existe un actor con ese id", exception.getMessage());
    }

    // Test para eliminar un actor exitosamente
    @Test
    void testDeleteActor() throws InvalidDataException {
        actoresService.delete(validActor);

        verify(actorRepository, times(1)).delete(validActor);
    }

    // Test para eliminar un actor con datos inválidos
    @Test
    void testDeleteActorWithInvalidData() {
        Actor invalidActor = new Actor(-1, null, null);

        InvalidDataException exception = assertThrows(InvalidDataException.class, () -> {
            actoresService.delete(invalidActor);
        });

        assertEquals("El nombre no puede ser nulo", exception.getMessage());
    }

    // Test para eliminar un actor por ID
    @Test
    void testDeleteById() {
        actoresService.deleteById(validActor.getActorId());

        verify(actorRepository, times(1)).deleteById(validActor.getActorId());
    }

    // Test para obtener novedades (actores con una fecha de actualización posterior)
    @Test
    void testNovedades() {
        Timestamp fecha = Timestamp.valueOf("2025-03-19 00:00:00");
        List<Actor> actors = List.of(new Actor(1, "John", "Doe"));
        when(actorRepository.findByLastUpdateGreaterThanEqualOrderByLastUpdate(fecha)).thenReturn(actors);

        List<Actor> result = actoresService.novedades(fecha);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("John", result.get(0).getFirstName());
    }

    // Test para manejar la excepción en el método add cuando el actor es nulo
    @Test
    void testAddActorWithNull() {
        InvalidDataException exception = assertThrows(InvalidDataException.class, () -> {
            actoresService.add(null);
        });

        assertEquals("No puede ser nulo", exception.getMessage());
    }

    // Test para manejar la excepción en el método delete cuando el actor es nulo
    @Test
    void testDeleteActorWithNull() {
        InvalidDataException exception = assertThrows(InvalidDataException.class, () -> {
            actoresService.delete(null);
        });

        assertEquals("No puede ser nulo", exception.getMessage());
    }

    // Test para verificar si el método repartePremios está vacío (sin implementación)
    @Test
    void testRepartePremios() {
        actoresService.repartePremios();  // No hace nada, por lo que no es necesario hacer más verificaciones.
    }
}
