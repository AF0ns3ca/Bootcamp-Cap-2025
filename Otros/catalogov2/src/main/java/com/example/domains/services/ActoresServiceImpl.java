package com.example.domains.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.example.domains.contracts.repositories.ActorRepository;
import com.example.domains.contracts.services.ActoresService;
import com.example.domains.entities.Actor;
import com.example.exceptions.DuplicateKeyException;
import com.example.exceptions.InvalidDataException;
import com.example.exceptions.NotFoundException;



@Service
public class ActoresServiceImpl implements ActoresService {

    private ActorRepository dao;

    public ActoresServiceImpl(ActorRepository dao) {
        this.dao = dao;
    }

    @Override
    public List<Actor> getAll() {
        return dao.findAll();
    }

    @Override
    public Optional<Actor> getOne(Integer id) {
        return dao.findById(id);
    }

    // Tenemos que comprobar que no sea nulo y que no exista ya un registro con el
    // mismo valor
    // Habria que validar los datos antes de hacer cualquier consulta a la base de
    // datos para evitar hacer una con datos malos
    @Override
    public Actor add(Actor item) throws DuplicateKeyException, InvalidDataException {
        if (item == null || item.getFirstName() == null || item.getFirstName().isEmpty() || item.getLastName() == null
                || item.getLastName().isEmpty()) {
            throw new InvalidDataException("No se puede añadir un valor nulo");
        }
        // Comprobamos primero que es mayor que cero para que no haga un viaje a la base
        // de datos para nada
        if (item.getActorId() > 0 && dao.existsById(item.getActorId())) {
            throw new DuplicateKeyException("Ya existe un actor con ese id");
        }

        return dao.save(item);
    }

    @Override
    public Actor modify(Actor item) throws InvalidDataException, NotFoundException {
        if (item == null || item.getFirstName() == null || item.getFirstName().isEmpty() || item.getLastName() == null
                || item.getLastName().isEmpty() || item.getActorId() <= 0) {
            throw new InvalidDataException("No se puede añadir un valor nulo");
        }

        if ((item.getActorId() < 0) || (!dao.existsById(item.getActorId()))) {
            throw new NotFoundException("No existe un actor con ese id");
        }

        return dao.save(item);
    }

    @Override
    public void delete(Actor item) throws InvalidDataException {
        if (item == null || item.getActorId() <= 0 || item.getFirstName() == null || item.getFirstName().isEmpty() || item.getLastName() == null || item.getLastName().isEmpty()) {
            throw new InvalidDataException("No se puede añadir un valor nulo");
        }
        dao.delete(item);
    }

    @Override
    public void deleteById(Integer id) {

        dao.deleteById(id);
    }

    public void GreaterThanId(int id) {
        dao.findByActorIdGreaterThan(id).forEach(System.err::println);
    }

}
