package com.example.domains.contracts.services;

import com.example.domains.core.contracts.services.DomainService;
import com.example.domains.entities.Film;

public interface FilmService extends DomainService<Film, Integer> {
    void filmWithCategories(int id);
    void filmWithActors(int id);
    void addActorToFilm(int filmId, int actorId);

}
