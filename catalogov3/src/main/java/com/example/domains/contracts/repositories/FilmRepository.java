package com.example.domains.contracts.repositories;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.example.domains.core.contracts.repositories.ProjectionsAndSpecificationJpaRepository;
import com.example.domains.entities.Film;
import com.example.domains.entities.models.FilmDetailsDTO;

public interface FilmRepository extends ProjectionsAndSpecificationJpaRepository<Film, Integer> {
	List<Film> findByLastUpdateGreaterThanEqualOrderByLastUpdate(Timestamp fecha);
	@Query("SELECT f FROM Film f JOIN FETCH f.filmActors fa JOIN FETCH fa.actor a where f.filmId = ?1")
    List<FilmDetailsDTO> findFilmsWithActors(int id);
}
