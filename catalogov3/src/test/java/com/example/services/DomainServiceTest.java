package com.example.services;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.example.exceptions.DuplicateKeyException;
import com.example.exceptions.InvalidDataException;
import com.example.exceptions.NotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Optional;
import java.util.List;

import com.example.domains.entities.Actor;
import com.example.domains.core.contracts.services.DomainService;


class DomainServiceTest {

    @Mock
    private DomainService<Actor, Integer> domainService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAll() {
        Actor actor1 = new Actor(1, "Lionel", "Messi");
        Actor actor2 = new Actor(2, "Cristiano", "Ronaldo");

        when(domainService.getAll()).thenReturn(List.of(actor1, actor2));

        List<Actor> actors = domainService.getAll();

        assertEquals(2, actors.size());
        assertEquals("Lionel", actors.get(0).getFirstName());
    }

    @Test
    void testGetOne() {
        Actor actor = new Actor(1, "Lionel", "Messi");

        when(domainService.getOne(1)).thenReturn(Optional.of(actor));

        Optional<Actor> result = domainService.getOne(1);

        assertTrue(result.isPresent());
        assertEquals("Lionel", result.get().getFirstName());
    }

    @Test
    void testAdd() throws DuplicateKeyException, InvalidDataException {
        Actor actor = new Actor(1, "Lionel", "Messi");

        when(domainService.add(actor)).thenReturn(actor);

        Actor result = domainService.add(actor);

        assertNotNull(result);
        assertEquals("Lionel", result.getFirstName());
    }

    @Test
    void testModify() throws NotFoundException, InvalidDataException {
        Actor actor = new Actor(1, "UpdatedActor", "UpdatedLastName");

        when(domainService.modify(actor)).thenReturn(actor);
        
        Actor result = domainService.modify(actor);
      
        assertNotNull(result);
        assertEquals("UpdatedActor", result.getFirstName());
    }

    @Test
    void testDelete() throws InvalidDataException {
        Actor actor = new Actor(1, "Lionel", "Messi");

        doNothing().when(domainService).delete(actor);
        
        domainService.delete(actor);
        
        verify(domainService, times(1)).delete(actor);
    }

    @Test
    void testDeleteById() {
        
        doNothing().when(domainService).deleteById(1);
        
        domainService.deleteById(1);
        
        verify(domainService, times(1)).deleteById(1);
    }
}

