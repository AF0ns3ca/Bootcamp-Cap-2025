package com.example.domains.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.domains.contracts.services.CategoryService;
import com.example.domains.entities.Category;
import com.example.exceptions.DuplicateKeyException;
import com.example.exceptions.InvalidDataException;
import com.example.exceptions.NotFoundException;

import com.example.domains.contracts.repositories.CategoryRepository;

@Service
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository dao;

    public CategoryServiceImpl(CategoryRepository dao) {
        this.dao = dao;
    }

    @Override
    public List<Category> getAll() {
        // TODO Auto-generated method stub
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
    public Category add(Category item) throws DuplicateKeyException, InvalidDataException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void delete(Category item) throws InvalidDataException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void deleteById(Integer id) {
        // TODO Auto-generated method stub
        
    }

    

    @Override
    public Optional<Category> getOne(Integer id) {
        // TODO Auto-generated method stub
        return Optional.empty();
    }

    @Override
    public Category modify(Category item) throws NotFoundException, InvalidDataException {
        // TODO Auto-generated method stub
        return null;
    }
    

}
