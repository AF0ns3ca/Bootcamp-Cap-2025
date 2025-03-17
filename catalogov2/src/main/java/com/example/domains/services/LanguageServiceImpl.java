package com.example.domains.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.domains.contracts.services.LanguageService;
import com.example.domains.entities.Language;
import com.example.exceptions.DuplicateKeyException;
import com.example.exceptions.InvalidDataException;
import com.example.exceptions.NotFoundException;

import com.example.domains.contracts.repositories.LanguageRepository;

@Service
public class LanguageServiceImpl implements LanguageService {

    private LanguageRepository dao;

    public LanguageServiceImpl(LanguageRepository dao) {
        this.dao = dao;
    }

    @Override
    public List<Language> getAll() {
        return dao.findAll();
    }

    @Override
    public Language add(Language item) throws DuplicateKeyException, InvalidDataException {
        if (item.isInvalid()) {
            throw new InvalidDataException("No se puede añadir un valor nulo"); 
        }
        //Comprobamos primero que es mayor que cero para que no haga un viaje a la base de datos para nada
        if(item.getLanguageId() > 0 && dao.existsById(item.getLanguageId())) {
            throw new DuplicateKeyException("Ya existe un lenguaje con ese id");
        }

        return dao.save(item);
    }

    @Override
    public Language modify(Language item) throws NotFoundException, InvalidDataException {
        if (item.isInvalid()) {
            throw new InvalidDataException("No se puede añadir un valor nulo"); 
        }

        if((item.getLanguageId() < 0) || (!dao.existsById(item.getLanguageId()))) {
            throw new NotFoundException("No existe un lenguaje con ese id");
        }
        
        return dao.save(item);
    }

    @Override
    public Optional<Language> getOne(Integer id) {
        // TODO Auto-generated method stub
        return dao.findById(id);
    }

    @Override
    public boolean equals(Object obj) {
        // TODO Auto-generated method stub
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        // TODO Auto-generated method stub
        return super.hashCode();
    }

    @Override
    public void delete(Language item) throws InvalidDataException {
        // TODO Auto-generated method stub

    }

    @Override
    public void deleteById(Integer id) {
        // TODO Auto-generated method stub

    }

    

}
