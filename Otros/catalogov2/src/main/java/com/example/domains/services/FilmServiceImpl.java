package com.example.domains.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.domains.contracts.services.FilmService;
import com.example.domains.entities.Actor;
import com.example.domains.entities.Film;
import com.example.domains.entities.FilmActor;
import com.example.domains.entities.FilmActorPK;
import com.example.exceptions.DuplicateKeyException;
import com.example.exceptions.InvalidDataException;
import com.example.exceptions.NotFoundException;
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.OneToMany;

import com.example.domains.contracts.repositories.ActorRepository;
import com.example.domains.contracts.repositories.FilmRepository;



@Service
public class FilmServiceImpl implements FilmService {

    @Autowired
    private FilmRepository dao;

    @Autowired
    private ActorRepository actorRepository;
    

    public FilmServiceImpl(FilmRepository dao) {
        this.dao = dao;
    }

    @Override
    public List<Film> getAll() {
        return dao.findAll();
    }

    @Override
    public Optional<Film> getOne(Integer id) {
        return dao.findById(id);
    }

    @Override
    public Film add(Film item) throws DuplicateKeyException, InvalidDataException {
        if (item == null) {
            throw new InvalidDataException("No se puede añadir un valor nulo"); 
        }
        //Comprobamos primero que es mayor que cero para que no haga un viaje a la base de datos para nada
        if(item.getFilmId() > 0 && dao.existsById(item.getFilmId())) {
            throw new DuplicateKeyException("Ya existe una película con ese id");
        }

        return dao.save(item);
    }

    @Override
    public Film modify(Film item) throws NotFoundException, InvalidDataException {
        if (item == null) {
            throw new InvalidDataException("No se puede añadir un valor nulo"); 
        }

        if((item.getFilmId() < 0) || (!dao.existsById(item.getFilmId()))) {
            throw new NotFoundException("No existe una película con ese id");
        }
        
        return dao.save(item);
    }

    @Override
    public void delete(Film item) throws InvalidDataException {
        if (item == null || item.getFilmId() < 0) {
            throw new InvalidDataException("No se puede añadir un valor nulo"); 
        }
        dao.delete(item);
        
    }

    @Override
    public void deleteById(Integer id) {
        dao.deleteById(id);
        
    }

    

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public void filmWithCategories(int id) {
        dao.findAllFilmsWithCategories(id).forEach(film -> {
            System.out.print(film.getTitle() + " - ");
            film.getFilmCategories().forEach(fc -> {
                System.out.println(fc.getCategory().getName());
            });
        });
        
    }

    public void filmWithActors(int id) {
        dao.findAllFilmsWithActors(id).forEach(film -> {
            System.out.print("\nPELICULA - ");
            System.out.println(film.getTitle() + "\n");
            System.out.println("REPARTO\n");
            film.getFilmActors().forEach(fa -> {
                System.out.println(fa.getActor().getFirstName() + " " + fa.getActor().getLastName());
            });
        });

        
        
    }  
    
      public void addActorToFilm(int filmId, int actorId) {
       
        Film film = dao.findById(filmId).orElseThrow(() -> new RuntimeException("Film not found"));  
        Actor actor = actorRepository.findById(actorId).orElseThrow(() -> new RuntimeException("Actor not found"));
 
        if (film.getFilmActors() == null) {
            film.setFilmActors(new ArrayList<>()); 
        }
        
        boolean actorExists = film.getFilmActors().stream()
        .anyMatch(filmActor -> filmActor.getActor().getActorId() == actor.getActorId());

        if (actorExists) {
            System.out.println("El actor ya está asociado con esta película.");
            return; 
        }
        
        FilmActor filmActor = new FilmActor();
        FilmActorPK pk = new FilmActorPK();
        pk.setFilmId(film.getFilmId());
        pk.setActorId(actor.getActorId());
        filmActor.setId(pk);
        filmActor.setFilm(film);
        filmActor.setActor(actor);

        film.addFilmActor(filmActor);

        dao.save(film);
    }
    
}
