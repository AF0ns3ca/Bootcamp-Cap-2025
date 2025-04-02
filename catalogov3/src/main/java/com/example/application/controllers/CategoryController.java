package com.example.application.controllers;


import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

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

import com.example.domains.contracts.services.CategoryService;
import com.example.domains.entities.Category;
import com.example.domains.entities.models.FilmDetailsDTO;
import com.example.domains.entities.models.FilmShortDTO;
import com.example.exceptions.BadRequestException;
import com.example.exceptions.DuplicateKeyException;
import com.example.exceptions.InvalidDataException;
import com.example.exceptions.NotFoundException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/category/v1")
@Tag(name = "Controlador Categorias", description = "Gestión de Categorías")
public class CategoryController {

    private CategoryService srv;

    public CategoryController(CategoryService srv) {
        super();
        this.srv = srv;
    }

    @Operation(summary = "Obtener todas las categorias")
    @GetMapping
    public List<Category> getAll() {
        return srv.getAll();
    }

    @Operation(summary = "Obtener una categoria por su id")
    @GetMapping(path = "/{id}")
    public Category getOne(@PathVariable @Parameter(description = "Identificador de la pelicula") int id)
            throws NotFoundException {
        var item = srv.getOne(id);
        if (item.isEmpty()) {
            throw new NotFoundException("No se encontro la pelicula con id " + id);
        }

        return item.get();

    }

    @GetMapping(path = "/{id}/peliculas")
	@Transactional
	public List<FilmDetailsDTO> getPelis(@PathVariable int id) throws NotFoundException {
		var Category = srv.getOne(id);
		if(Category.isEmpty())
			throw new NotFoundException();
		else {
			return (List<FilmDetailsDTO>) Category.get().getFilmCategories().stream()
					.map(item -> FilmDetailsDTO.from(item.getFilm()))
					.collect(Collectors.toList());
		}
	}

    @PostMapping
    @ApiResponse(responseCode = "201", description = "Categoría creada")
    public ResponseEntity<Object> create(@Valid @RequestBody Category item) throws BadRequestException, DuplicateKeyException , InvalidDataException {
        var newItem = srv.add(item);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(newItem.getCategoryId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping(path = "/{id}")
    @ApiResponse(responseCode = "200", description = "Categoría modificada")
    public void update(@PathVariable @Parameter(description = "Identificador de la pelicula") int id, @Valid @RequestBody Category item) throws NotFoundException, InvalidDataException, BadRequestException {
        if (item.getCategoryId() != id) {
            throw new BadRequestException("El id no coincide con el id de la URL");
        }
        
        item.setCategoryId(id);
        srv.modify(item);
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable @Parameter(description = "Identificador de la categoria") int id) throws NotFoundException {
            srv.deleteById(id);
    }

}
