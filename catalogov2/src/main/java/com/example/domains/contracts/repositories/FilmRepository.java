package com.example.domains.contracts.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.example.domains.entities.Film;

import java.util.List;

public interface FilmRepository extends JpaRepository<Film, Integer>, JpaSpecificationExecutor<Film> {

    @Query("SELECT f FROM Film f JOIN FETCH f.filmCategories fc JOIN FETCH fc.category c where f.filmId = ?1")
    List<Film> findAllFilmsWithCategories(int id);
    @Query("SELECT f FROM Film f JOIN FETCH f.filmActors fa JOIN FETCH fa.actor a where f.filmId = ?1")
    List<Film> findAllFilmsWithActors(int id);
}