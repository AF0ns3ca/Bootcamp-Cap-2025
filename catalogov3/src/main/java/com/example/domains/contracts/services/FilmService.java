package com.example.domains.contracts.services;

import java.sql.Timestamp;
import java.util.List;

import com.example.domains.core.contracts.services.ProjectionDomainService;
import com.example.domains.core.contracts.services.SpecificationDomainService;
import com.example.domains.entities.Film;
import com.example.domains.entities.models.FilmDetailsDTO;

public interface FilmService extends ProjectionDomainService<Film, Integer>, SpecificationDomainService<Film, Integer> {
	List<Film> novedades(Timestamp fecha);
	FilmDetailsDTO filmWithActors(int id);
}
