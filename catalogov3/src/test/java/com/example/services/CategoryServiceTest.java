package com.example.services;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;
import java.util.List;

import com.example.domains.entities.Category;
import com.example.exceptions.DuplicateKeyException;
import com.example.exceptions.InvalidDataException;
import com.example.exceptions.NotFoundException;
import com.example.domains.contracts.repositories.CategoryRepository;
import com.example.domains.services.CategoryServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

public class CategoryServiceTest {

    @Mock
    private CategoryRepository mockCategoryRepository;

    private CategoryServiceImpl categoryService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        categoryService = new CategoryServiceImpl(mockCategoryRepository);
    }

    @Test
    public void testAddCategoryValid() throws DuplicateKeyException, InvalidDataException {
        
        Category category = new Category();
        category.setCategoryId(1);
        category.setName("Electronics");
        when(mockCategoryRepository.existsById(category.getCategoryId())).thenReturn(false);
        when(mockCategoryRepository.save(category)).thenReturn(category);

      
        Category result = categoryService.add(category);

       
        assertNotNull(result);
        assertEquals(category.getCategoryId(), result.getCategoryId());
        assertEquals(category.getName(), result.getName());
    }

    @Test
    public void testAddCategoryDuplicateKey() throws DuplicateKeyException, InvalidDataException {
       
        Category category = new Category();
        category.setCategoryId(1);
        when(mockCategoryRepository.existsById(category.getCategoryId())).thenReturn(true);

     
        DuplicateKeyException thrown = assertThrows(DuplicateKeyException.class, () -> {
            categoryService.add(category);
        });
        assertEquals("Ya existe una categoria con ese id", thrown.getMessage());
    }

    @Test
    public void testAddCategoryInvalidName() {
       
        Category invalidCategory = null;

      
        InvalidDataException thrown = assertThrows(InvalidDataException.class, () -> {
            categoryService.add(invalidCategory);
        });
        assertEquals("No se puede añadir un valor nulo", thrown.getMessage());
    }

    @Test
    public void testModifyCategory() throws NotFoundException, InvalidDataException {
        
        Category category = new Category();
        category.setCategoryId(1);
        category.setName("Electronics");
        when(mockCategoryRepository.existsById(category.getCategoryId())).thenReturn(true);
        when(mockCategoryRepository.save(category)).thenReturn(category);

       
        Category result = categoryService.modify(category);

       
        assertNotNull(result);
        assertEquals(category.getCategoryId(), result.getCategoryId());
    }

    @Test
    public void testModifyCategoryNotFound() {
        
        Category category = new Category();
        category.setCategoryId(1);
        when(mockCategoryRepository.existsById(category.getCategoryId())).thenReturn(false);

       
        NotFoundException thrown = assertThrows(NotFoundException.class, () -> {
            categoryService.modify(category);
        });
        assertEquals("No existe una categoria con ese id", thrown.getMessage());
    }

    @Test
    public void testDeleteCategory() throws InvalidDataException {
      
        Category category = new Category();
        category.setCategoryId(1);
        category.setName("Electronics");
        when(mockCategoryRepository.existsById(category.getCategoryId())).thenReturn(true);

     
        categoryService.delete(category);

        
        verify(mockCategoryRepository).delete(category);
    }

    @Test
    public void testDeleteCategoryInvalid() {
       
        Category invalidCategory = new Category();
        invalidCategory.setCategoryId(-1); 

       
        InvalidDataException thrown = assertThrows(InvalidDataException.class, () -> {
            categoryService.delete(invalidCategory);
        });
        assertEquals("No se puede añadir un valor nulo", thrown.getMessage());
    }

    @Test
    public void testDeleteById() {
        
        Integer categoryId = 1;

       
        categoryService.deleteById(categoryId);

        
        verify(mockCategoryRepository).deleteById(categoryId);
    }

    @Test
    public void testGetAllCategories() {
      
        Category category = new Category();
        category.setCategoryId(1);
        category.setName("Electronics");
        when(mockCategoryRepository.findAll()).thenReturn(List.of(category));

       
        var categories = categoryService.getAll();

      
        assertFalse(categories.isEmpty());
        assertEquals(1, categories.size());
    }

    @Test
    public void testGetOneCategory() {
     
        Category category = new Category();
        category.setCategoryId(1);
        category.setName("Electronics");
        when(mockCategoryRepository.findById(category.getCategoryId())).thenReturn(Optional.of(category));

       
        Optional<Category> result = categoryService.getOne(category.getCategoryId());

      
        assertTrue(result.isPresent());
        assertEquals(category.getCategoryId(), result.get().getCategoryId());
    }

    @Test
    public void testGetOneCategoryNotFound() {
       
        Integer categoryId = 1;
        when(mockCategoryRepository.findById(categoryId)).thenReturn(Optional.empty());

     
        Optional<Category> result = categoryService.getOne(categoryId);

        
        assertFalse(result.isPresent());
    }
}

