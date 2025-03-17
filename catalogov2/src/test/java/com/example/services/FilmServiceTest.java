package com.example.services;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import com.example.domains.entities.Film;
import com.example.exceptions.DuplicateKeyException;
import com.example.exceptions.InvalidDataException;
import com.example.exceptions.NotFoundException;
import com.example.domains.contracts.repositories.FilmRepository;
import com.example.domains.services.FilmServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

public class FilmServiceTest {

    @Mock
    private FilmRepository mockFilmRepository;

    private FilmServiceImpl filmService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        filmService = new FilmServiceImpl(mockFilmRepository);
    }

    @Test
    public void testAddFilmValid() throws DuplicateKeyException, InvalidDataException {
        Film film = new Film();
        film.setFilmId(1);
        film.setTitle("Inception");
        when(mockFilmRepository.existsById(film.getFilmId())).thenReturn(false);
        when(mockFilmRepository.save(film)).thenReturn(film);

        Film result = filmService.add(film);

        assertNotNull(result);
        assertEquals(film.getFilmId(), result.getFilmId());
        assertEquals(film.getTitle(), result.getTitle());
    }

    @Test
    public void testAddFilmDuplicateKey() throws DuplicateKeyException, InvalidDataException {
        Film film = new Film();
        film.setFilmId(1);
        when(mockFilmRepository.existsById(film.getFilmId())).thenReturn(true);

        DuplicateKeyException thrown = assertThrows(DuplicateKeyException.class, () -> {
            filmService.add(film);
        });
        assertEquals("Ya existe una película con ese id", thrown.getMessage());
    }

    @Test
    public void testAddFilmInvalid() {
        Film invalidFilm = null;

        InvalidDataException thrown = assertThrows(InvalidDataException.class, () -> {
            filmService.add(invalidFilm);
        });
        assertEquals("No se puede añadir un valor nulo", thrown.getMessage());
    }

    @Test
    public void testModifyFilm() throws NotFoundException, InvalidDataException {
        Film film = new Film();
        film.setFilmId(1);
        film.setTitle("The Dark Knight");
        when(mockFilmRepository.existsById(film.getFilmId())).thenReturn(true);
        when(mockFilmRepository.save(film)).thenReturn(film);

        Film result = filmService.modify(film);

        assertNotNull(result);
        assertEquals(film.getFilmId(), result.getFilmId());
    }

    @Test
    public void testModifyFilmNotFound() {
        Film film = new Film();
        film.setFilmId(1);
        when(mockFilmRepository.existsById(film.getFilmId())).thenReturn(false);

        NotFoundException thrown = assertThrows(NotFoundException.class, () -> {
            filmService.modify(film);
        });
        assertEquals("No existe una película con ese id", thrown.getMessage());
    }

    @Test
    public void testModifyFilmInvalid() {
        Film invalidFilm = new Film();
        invalidFilm.setFilmId(-1);

        NotFoundException thrown = assertThrows(NotFoundException.class, () -> {
            filmService.modify(invalidFilm);
        });
        assertEquals("No existe una película con ese id", thrown.getMessage());
    }

    @Test
    public void testDeleteFilm() throws InvalidDataException {
        Film film = new Film();
        film.setFilmId(1);
        film.setTitle("The Matrix");
        when(mockFilmRepository.existsById(film.getFilmId())).thenReturn(true);

        filmService.delete(film);

        verify(mockFilmRepository).delete(film);
    }

    @Test
    public void testDeleteFilmInvalid() {
        Film invalidFilm = new Film();
        invalidFilm.setFilmId(-1); 

        InvalidDataException thrown = assertThrows(InvalidDataException.class, () -> {
            filmService.delete(invalidFilm);
        });
        assertEquals("No se puede añadir un valor nulo", thrown.getMessage());
    }

    @Test
    public void testDeleteFilmNull() {
        Film nullFilm = null;

        InvalidDataException thrown = assertThrows(InvalidDataException.class, () -> {
            filmService.delete(nullFilm);
        });
        assertEquals("No se puede añadir un valor nulo", thrown.getMessage());
    }

    @Test
    public void testDeleteFilmById() {
        Integer filmId = 1;

        filmService.deleteById(filmId);

        verify(mockFilmRepository).deleteById(filmId);
    }

    @Test
    public void testGetAllFilms() {
        Film film = new Film();
        film.setFilmId(1);
        film.setTitle("Titanic");
        when(mockFilmRepository.findAll()).thenReturn(List.of(film));

        var films = filmService.getAll();

        assertFalse(films.isEmpty());
        assertEquals(1, films.size());
    }

    @Test
    public void testGetOneFilm() {
        Film film = new Film();
        film.setFilmId(1);
        film.setTitle("The Godfather");
        when(mockFilmRepository.findById(film.getFilmId())).thenReturn(Optional.of(film));

        Optional<Film> result = filmService.getOne(film.getFilmId());

        assertTrue(result.isPresent());
        assertEquals(film.getFilmId(), result.get().getFilmId());
    }

    @Test
    public void testGetOneFilmNotFound() {
        Integer filmId = 1;
        when(mockFilmRepository.findById(filmId)).thenReturn(Optional.empty());

        Optional<Film> result = filmService.getOne(filmId);

        assertFalse(result.isPresent());
    }

    @Test
    public void testFilmWithCategories() {
        Film film = new Film();
        film.setFilmId(1);
        film.setTitle("Inception");

        filmService.FilmWithCategories(film.getFilmId());

        verify(mockFilmRepository).findAllFilmsWithCategories(film.getFilmId());
    }


    @Test
    public void testFilmWithCategoriesNoCategories() {
        Film film = new Film();
        film.setFilmId(1);
        film.setTitle("Inception");

        when(mockFilmRepository.findAllFilmsWithCategories(film.getFilmId())).thenReturn(List.of());

        filmService.FilmWithCategories(film.getFilmId());

        verify(mockFilmRepository).findAllFilmsWithCategories(film.getFilmId());
    }

    
}
