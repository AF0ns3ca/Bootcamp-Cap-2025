package com.example.application.controllers;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.domains.contracts.services.FilmService;
import com.example.domains.entities.Category;
import com.example.domains.entities.Film;
import com.example.domains.entities.models.FilmDetailsDTO;
import com.example.domains.entities.models.FilmEditDTO;
import com.example.domains.entities.models.FilmPostDTO;
import com.example.domains.entities.models.FilmShortDTO;
import com.example.exceptions.BadRequestException;
import com.example.exceptions.DuplicateKeyException;
import com.example.exceptions.InvalidDataException;
import com.example.exceptions.NotFoundException;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

@RestController
@RequestMapping("/films/v1")
@Tag(name = "Controlador Peliculas", description = "Gestión de Peliculas")
public class FilmController {

    private FilmService srv;

    public FilmController(FilmService srv) {
        super();
        this.srv = srv;
    }

    
    @GetMapping(produces = "application/json")
    @Hidden
    public List<FilmShortDTO> getAll() {
        return srv.getByProjection(FilmShortDTO.class);
    }

    @Operation(summary = "Obtiene las películas paginadas")
    @GetMapping(path = "/", params = { "page" })
    public Page<FilmShortDTO> getAll(Pageable pageable) {
        return srv.getByProjection(pageable, FilmShortDTO.class);
    }

    @Operation(summary = "Obtener una pelicula por su id")
    @GetMapping(path = "/{id}")
    @ApiResponse(responseCode = "200", description = "Pelicula encontrada")
    @ApiResponse(responseCode = "404", description = "Pelicula no encontrada")
    public FilmShortDTO getOne(@PathVariable @Parameter(description = "Identificador de la pelicula") int id)
            throws NotFoundException {
        var item = srv.getOne(id);
        if (item.isEmpty()) {
            throw new NotFoundException("No se encontro la pelicula con id " + id);
        }
        return FilmShortDTO.from(item.get());

    }

    // @GetMapping(path = "/{id}", params = "mode=details")
	// public FilmDetailsDTO getOneDetalle(
	// 		@Parameter(description = "Identificador de la pelicula", required = true) @PathVariable int id,
	// 		@Parameter(required = false, schema = @Schema(type = "string", allowableValues = { "details", "short",
	// 				"edit" }, defaultValue = "edit")) @RequestParam(required = false, defaultValue = "edit") String mode)
	// 		throws Exception {
	// 	Optional<Film> rslt = srv.getOne(id);
	// 	if (rslt.isEmpty())
	// 		throw new NotFoundException();
	// 	return FilmDetailsDTO.from(rslt.get());
	// }

    // @Operation(summary = "Recupera una pelicula", description = "Version corta, version detallada, version donde se han traido las dependencias con sus identificadores y version donde se han convertido dependencias en cadena.")
	// @GetMapping(path = "/{id}")
	// @ApiResponse(responseCode = "200", description = "Pelicula encontrada", content = @Content(schema = @Schema(oneOf = {
	// 		FilmShortDTO.class, FilmDetailsDTO.class, FilmEditDTO.class })))
	// @ApiResponse(responseCode = "404", description = "Pelicula no encontrada", content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
	// public FilmEditDTO getOneEditar(
	// 		@Parameter(description = "Identificador de la pelicula", required = true) 
	// 		@PathVariable 
	// 		int id,
	// 		@Parameter(required = false, schema = @Schema(type = "string", allowableValues = { "details", "short",
	// 				"edit" }, defaultValue = "edit")) 
	// 		@RequestParam(required = false, defaultValue = "edit") 
	// 		String mode)
	// 		throws Exception {
	// 	Optional<Film> rslt = srv.getOne(id);
	// 	if (rslt.isEmpty())
	// 		throw new NotFoundException();
	// 	return FilmEditDTO.from(rslt.get());
	// }

    @Operation(summary = "Obtener los actores de una pelicula")
    @GetMapping(path = "/{id}/actores")
    public FilmDetailsDTO getActors(@PathVariable int id) {
        return srv.filmWithActors(id);
    }

    @Operation(summary = "Categorias")
	@ApiResponse(responseCode = "200", description = "Pelicula encontrada")
	@ApiResponse(responseCode = "404", description = "Pelicula no encontrada")
	@GetMapping(path = "/{id}/categorias")
	@Transactional
	public List<Category> getCategories(
			@Parameter(description = "Identificador de la pelicula", required = true) @PathVariable int id)
			throws Exception {
		Optional<Film> rslt = srv.getOne(id);
		if (rslt.isEmpty())
			throw new NotFoundException();
		return rslt.get().getCategories();
	}

    @Operation(summary = "Buscar peliculas por titulo")
    @GetMapping(params = { "title" })
    @ApiResponse(responseCode = "200", description = "Peliculas encontradas")
    @ApiResponse(responseCode = "404", description = "Peliculas no encontradas")
    public List<FilmDetailsDTO> findByTitle(@Parameter(description = "Titulo de la pelicula") String title) {
        return srv.findByTitle(title);
    }

    @Operation(summary = "Buscar peliculas por titulo paginadas")
    @GetMapping(path = { "/title" })
    public Page<FilmDetailsDTO> findByTitle(@RequestParam String title,
            @ParameterObject Pageable pageable) {
        return srv.findByTitlePage(title, pageable);
    }

    @PostMapping
    @Operation(summary = "Añadir una nueva pelicula con detalles reducidos")
    @ApiResponse(responseCode = "201", description = "Pelicula creada")
    @ApiResponse(responseCode = "400", description = "Datos invalidos")
    @ApiResponse(responseCode = "409", description = "Pelicula duplicada")
    public ResponseEntity<Object> create(@Valid @RequestBody FilmPostDTO item) throws BadRequestException, DuplicateKeyException , InvalidDataException {
        var newItem = srv.add(FilmPostDTO.from(item));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(newItem.getFilmId()).toUri();
        return ResponseEntity.created(location).build();
    }

    // @Operation(summary = "Añadir una nueva pelicula")
	// @ApiResponse(responseCode = "201", description = "Pelicula añadida")
	// @ApiResponse(responseCode = "404", description = "Pelicula no encontrada")
	// @PostMapping
	// @ResponseStatus(code = HttpStatus.CREATED)
	// @Transactional
	// public ResponseEntity<Object> add(@RequestBody() FilmEditDTO item) throws Exception {
	// 	Film newItem = srv.add(FilmEditDTO.from(item));
	// 	URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
	// 			.buildAndExpand(newItem.getFilmId()).toUri();
	// 	return ResponseEntity.created(location).build();
	// }

    @Operation(summary = "Modificar una pelicula existente de forma reducida")
	@ApiResponse(responseCode = "200", description = "Pelicula encontrada")
	@ApiResponse(responseCode = "404", description = "Pelicula no encontrada")
	@PutMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable int id, @Valid @RequestBody FilmPostDTO item)
            throws BadRequestException, NotFoundException, InvalidDataException {
        if (item.getFilmId() != id) {
            throw new BadRequestException("El id no coincide con el id de la URL");
        }
        srv.modify(FilmPostDTO.from(item));

    }

    // @Operation(summary = "Modificar una pelicula existente de forma detallada")
	// @ApiResponse(responseCode = "200", description = "Pelicula encontrada")
	// @ApiResponse(responseCode = "404", description = "Pelicula no encontrada")
	// @PutMapping(path = "/{id}")
	// public FilmEditDTO modify(
	// 		@Parameter(description = "Identificador de la pelicula", required = true) @PathVariable int id,
	// 		@Valid @RequestBody FilmEditDTO item) throws Exception {
	// 	if (item.getFilmId() != id)
	// 		throw new BadRequestException("El id no coincide con el id de la URL");
	// 	return FilmEditDTO.from(srv.modify(FilmEditDTO.from(item)));
	// }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204", description = "Pelicula eliminada")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        srv.deleteById(id);
    }

}



