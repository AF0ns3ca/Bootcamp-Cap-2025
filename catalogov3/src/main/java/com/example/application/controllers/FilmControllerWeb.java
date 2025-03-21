package com.example.application.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.domains.contracts.services.FilmService;
import com.example.domains.entities.models.FilmShortDTO;

import io.swagger.v3.oas.annotations.tags.Tag;

@Controller
@Tag(name = "Controlador Peliculas", description = "Gestión de Peliculas")
public class FilmControllerWeb {

    private FilmService srv;

    public FilmControllerWeb(FilmService srv) {
        super();
        this.srv = srv;
    }

    @GetMapping("/list")
    public String getAllFilms(Model model) {
        List<FilmShortDTO> films = srv.getByProjection(FilmShortDTO.class);
        model.addAttribute("films", films);
        return "list";  // Este es el nombre de la plantilla Thymeleaf (list.html)
    }
}
