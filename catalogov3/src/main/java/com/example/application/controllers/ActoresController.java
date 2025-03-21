package com.example.application.controllers;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.HttpStatus;
import com.example.domains.entities.models.ActorDTO;
import com.example.domains.contracts.services.ActorService;
import com.example.exceptions.NotFoundException;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import com.example.exceptions.BadRequestException;
import com.example.exceptions.InvalidDataException;
import com.example.exceptions.DuplicateKeyException;
import org.springframework.data.domain.Pageable;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/actores/v1")
@Tag(name = "Controlador Actores", description = "Gestión de actores")
public class ActoresController {

    private ActorService srv;

    public ActoresController(ActorService srv) {
        super();
        this.srv = srv;
    }

    @Hidden
    @GetMapping
    public List<ActorDTO> getAll() {
        return srv.getByProjection(ActorDTO.class);
    }

    @Operation(summary = "Obtiene todos los actores paginados")
    @GetMapping(params = {"page"})
    public Page<ActorDTO> getAll(Pageable pageable) {
        return srv.getByProjection(pageable, ActorDTO.class);
    }

    @Operation(summary = "Obtener un actor por su id")
    @GetMapping(path = "/{id}")
    public ActorDTO getOne(@PathVariable @Parameter(description = "Identificador del actor") int id) throws NotFoundException {
        var item = srv.getOne(id);
        if (item.isEmpty()) {
            throw new NotFoundException("No se encontro el actor con id " + id);
        }
        return ActorDTO.from(item.get());

    }

     //Se puede crear un DTO en una clase interna si va a ser de un solo uso, laos tipos record son clases inmutables cuyos datos no pueden cambiar, es similar a lombok pero esto es sintaxis propia de java, pone datos, constructores, etc...
     record Titulo(int id, String titulo) { }

    @GetMapping(path = "/{id}/pelis")
    @Transactional
    public List<Titulo> getPeliculas(@PathVariable int id) throws NotFoundException {
        var item = srv.getOne(id);
        if (item.isEmpty()) {
            throw new NotFoundException("No se encontro el actor con id " + id);
        }
        return item.get().getFilmActors().stream().map(o -> new Titulo(o.getFilm().getFilmId(), o.getFilm().getTitle()))
        .toList();

    }
   

    @PostMapping
    @ApiResponse(responseCode = "201", description = "Actor creado")
    public ResponseEntity<Object> create(@Valid @RequestBody ActorDTO item) throws BadRequestException, DuplicateKeyException , InvalidDataException {
        var newItem = srv.add(ActorDTO.from(item));
        //Generacion de la url de forma dinamica para que cumpla el convenio REST de la API y sea del estilo /actores/v1/{id}
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(newItem.getActorId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable int id, @Valid @RequestBody ActorDTO item)
            throws BadRequestException, NotFoundException, InvalidDataException {
        if (item.getActorId() != id) {
            throw new BadRequestException("El id no coincide con el id de la URL");
        }
        srv.modify(ActorDTO.from(item));

    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        srv.deleteById(id);
    }

}
