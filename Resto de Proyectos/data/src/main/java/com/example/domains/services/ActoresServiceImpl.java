package com.example.domains.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.domains.contracts.repositories.ActorRepository;
import com.example.domains.contracts.services.ActoresService;
import com.example.domains.entities.Actor;
import com.example.exceptions.DuplicateKeyException;
import com.example.exceptions.InvalidDataException;


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

    //Tenemos que comprobar que no sea nulo y que no exista ya un registro con el mismo valor
    //Habria que validar los datos antes de hacer cualquier consulta a la base de datos para evitar hacer una con datos malos
    @Override
    public Actor add(Actor item) throws DuplicateKeyException, InvalidDataException{
        if (item == null) {
            throw new InvalidDataException("No se puede añadir un valor nulo"); 
        }
        //Comprobamos primero que es mayor que cero para que no haga un viaje a la base de datos para nada
        if(item.getActorId() > 0 && dao.existsById(item.getActorId())) {
            throw new DuplicateKeyException("Ya existe un actor con ese id");
        }

        return dao.save(item);
    }

    @Override
    public Actor modify(Actor item) {
        return dao.save(item);
    }

    @Override
    public void delete(Actor item) {
        dao.delete(item);
    }

    @Override
    public void deleteById(Integer id) {
        dao.deleteById(id);
    }

    public void GreaterThanId(int id) {
        // TODO Auto-generated method stub
        dao.findByActorIdGreaterThan(id).forEach(System.err::println);
    }




    @Override
    public void repartePremio() {
        // TODO Auto-generated method stub

    }



}
