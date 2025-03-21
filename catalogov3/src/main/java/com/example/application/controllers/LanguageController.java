package com.example.application.controllers;


import java.net.URI;
import java.util.List;

import org.springframework.http.HttpStatus;
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

import com.example.domains.contracts.services.LanguageService;
import com.example.domains.entities.Language;
import com.example.exceptions.BadRequestException;
import com.example.exceptions.DuplicateKeyException;
import com.example.exceptions.InvalidDataException;
import com.example.exceptions.NotFoundException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/language/v1")
@Tag(name = "Controlador Language", description = "Gestión de Categorías")
public class LanguageController {

    private LanguageService srv;

    public LanguageController(LanguageService srv) {
        super();
        this.srv = srv;
    }


    @GetMapping
    public List<Language> getAll() {
        return srv.getAll();
    }

    @Operation(summary = "Obtener un language por su id")
    @GetMapping(path = "/{id}")
    public Language getOne(@PathVariable @Parameter(description = "Identificador del language") int id) throws NotFoundException {
        var item = srv.getOne(id);
        if (item.isEmpty()) {
            throw new NotFoundException("No se encontro el actor con id " + id);
        }
       return item.get();

    }

    @PostMapping
    @ApiResponse(responseCode = "201", description = "Language creado")
    public ResponseEntity<Object> create(@Valid @RequestBody Language item) throws BadRequestException, DuplicateKeyException , InvalidDataException {
        var newItem = srv.add(item);
        //Generacion de la url de forma dinamica para que cumpla el convenio REST de la API y sea del estilo /actores/v1/{id}
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(newItem.getLanguageId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable int id, @Valid @RequestBody Language item)
            throws BadRequestException, NotFoundException, InvalidDataException {
        if (item.getLanguageId() != id) {
            throw new BadRequestException("El id no coincide con el id de la URL");
        }
        srv.modify(item);

    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        srv.deleteById(id);
    }

}
