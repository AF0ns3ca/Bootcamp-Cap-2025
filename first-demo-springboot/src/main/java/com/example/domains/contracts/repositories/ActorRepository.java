package com.example.domains.contracts.repositories;
import java.util.List;
import org.springframework.data.domain.Sort;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.example.domains.entities.Actor;

public interface ActorRepository extends JpaRepository<Actor, Integer>, JpaSpecificationExecutor<Actor> {

    //Devolver una lista de 10 actores ordenados por apellido en orden descendente cuyo nombre empiece por un prefijo
    List<Actor>findTop5ByFirstNameStartingWithOrderByLastNameDesc(String prefijo);
    List<Actor>findTop5ByFirstNameStartingWith(String prefijo, Sort orderBy);
    //Devolver una lista de actores cuyo id sea mayor que un valor dado
    List<Actor>findByActorIdGreaterThan(int id);

    //Ahora asumiremos nosotros la consulta
    @Query(value = "SELECT a FROM Actor a WHERE a.actorId > ?1")
    List<Actor>findNovedadesJPQL(int id);

    //Esta es en SQL por tanto la tabla se llama como en la base de datos, actor en vez de Actor, igualmente la columna sera actor_id en vez de actorId
    @Query(value = "SELECT * FROM actor a WHERE a.actor_id > :id", nativeQuery = true)
    List<Actor>findNovedadesSQL(int id);
}
