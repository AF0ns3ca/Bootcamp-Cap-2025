package com.example.application.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.domains.contracts.services.ActorService;
import com.example.domains.contracts.services.FilmService;
import com.example.domains.entities.Film;
import com.example.domains.entities.models.ActorDTO;
import com.example.domains.entities.models.FilmDetailsDTO;
import com.example.domains.entities.models.FilmShortDTO;
import com.example.exceptions.BadRequestException;
import com.example.exceptions.DuplicateKeyException;
import com.example.exceptions.InvalidDataException;
import com.example.exceptions.NotFoundException;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/films/v1")
@Tag(name = "Controlador Peliculas", description = "Gestión de Peliculas")
public class FilmController {

    private FilmService srv;

     public FilmController(FilmService srv) {
        super();
        this.srv = srv;
    }

    @Hidden
    @GetMapping
    public List<FilmShortDTO> getAll() {
        return srv.getByProjection(FilmShortDTO.class);
    }

    @Operation(summary = "Obtiene las películas paginadas")
    @GetMapping(params = {"page"})
    public Page<FilmShortDTO> getAll(Pageable pageable) {
        return srv.getByProjection(pageable, FilmShortDTO.class);
    }   

    @Operation(summary = "Obtener una pelicula por su id")
    @GetMapping(path = "/{id}")
    public FilmShortDTO getOne(@PathVariable @Parameter(description = "Identificador de la pelicula") int id) throws NotFoundException {
        var item = srv.getOne(id);
        if (item.isEmpty()) {
            throw new NotFoundException("No se encontro la pelicula con id " + id);
        }
        return FilmShortDTO.from(item.get());

    }

    @Operation(summary = "Obtener los actores de una pelicula")
    @GetMapping(path = "/{id}/actores")
    public FilmDetailsDTO getActors(@PathVariable int id) {
        return srv.filmWithActors(id);
    }

}
