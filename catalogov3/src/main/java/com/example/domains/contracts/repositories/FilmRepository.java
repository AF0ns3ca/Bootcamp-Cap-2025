package com.example.domains.contracts.repositories;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.domains.core.contracts.repositories.ProjectionsAndSpecificationJpaRepository;
import com.example.domains.entities.Film;
import com.example.domains.entities.models.FilmDetailsDTO;
import com.example.domains.entities.models.FilmEditDTO;

public interface FilmRepository extends ProjectionsAndSpecificationJpaRepository<Film, Integer> {
	List<Film> findByLastUpdateGreaterThanEqualOrderByLastUpdate(Timestamp fecha);

	@Query("SELECT f FROM Film f JOIN FETCH f.filmActors fa JOIN FETCH fa.actor a where f.filmId = ?1")
	FilmDetailsDTO findFilmsWithActors(int id);

	List<Film> findByTitleContaining(String title);

	Page<Film> findByTitleContaining(String title, Pageable pageable);
}
