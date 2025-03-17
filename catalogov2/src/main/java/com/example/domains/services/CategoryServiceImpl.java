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
        return dao.findAll();
    }

    @Override
    public Optional<Category> getOne(Integer id) {
        return dao.findById(id);
    }

    @Override
    public Category add(Category item) throws DuplicateKeyException, InvalidDataException {
        if (item.isInvalid()) {
            throw new InvalidDataException("No se puede añadir un valor nulo"); 
        }
        //Comprobamos primero que es mayor que cero para que no haga un viaje a la base de datos para nada
        if(item.getCategoryId() > 0 && dao.existsById(item.getCategoryId())) {
            throw new DuplicateKeyException("Ya existe una categoria con ese id");
        }

        return dao.save(item);
    }

    @Override
    public Category modify(Category item) throws NotFoundException, InvalidDataException {
        if (item.isInvalid()) {
            throw new InvalidDataException("No se puede añadir un valor nulo"); 
        }

        if((item.getCategoryId() < 0) || (!dao.existsById(item.getCategoryId()))) {
            throw new NotFoundException("No existe una categoria con ese id");
        }
        
        return dao.save(item);
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
    public void delete(Category item) throws InvalidDataException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void deleteById(Integer id) {
        // TODO Auto-generated method stub
        
    }
    

}
