package com.example.domains.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.domains.contracts.services.FilmService;
import com.example.domains.entities.Film;
import com.example.exceptions.DuplicateKeyException;
import com.example.exceptions.InvalidDataException;
import com.example.exceptions.NotFoundException;
import com.example.domains.contracts.repositories.FilmRepository;

@Service
public class FilmServiceImpl implements FilmService {

    private FilmRepository dao;

    public FilmServiceImpl(FilmRepository dao) {
        this.dao = dao;
    }

    @Override
    public List<Film> getAll() {
        // TODO Auto-generated method stub
        return dao.findAll();
    }

    @Override
    public Optional<Film> getOne(Integer id) {
        // TODO Auto-generated method stub
        return dao.findById(id);
    }

    @Override
    public Film add(Film item) throws DuplicateKeyException, InvalidDataException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void delete(Film item) throws InvalidDataException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void deleteById(Integer id) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public Film modify(Film item) throws NotFoundException, InvalidDataException {
        // TODO Auto-generated method stub
        return null;
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
    public String toString() {
        // TODO Auto-generated method stub
        return super.toString();
    }

    


}
