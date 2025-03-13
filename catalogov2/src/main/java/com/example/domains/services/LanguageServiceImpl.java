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

    @Override
    public Language add(Language item) throws DuplicateKeyException, InvalidDataException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void delete(Language item) throws InvalidDataException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void deleteById(Integer id) {
        // TODO Auto-generated method stub
        
    }

    

    @Override
    public Optional<Language> getOne(Integer id) {
        // TODO Auto-generated method stub
        return Optional.empty();
    }

    @Override
    public Language modify(Language item) throws NotFoundException, InvalidDataException {
        // TODO Auto-generated method stub
        return null;
    }

    
}
