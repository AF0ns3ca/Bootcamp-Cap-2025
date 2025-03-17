package com.example.services;


import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import com.example.domains.entities.Language;
import com.example.exceptions.DuplicateKeyException;
import com.example.exceptions.InvalidDataException;
import com.example.exceptions.NotFoundException;
import com.example.domains.contracts.repositories.LanguageRepository;
import com.example.domains.services.LanguageServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;



public class LanguageServiceTest {

    @Mock
    private LanguageRepository mockLanguageRepository;

    private LanguageServiceImpl languageService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        languageService = new LanguageServiceImpl(mockLanguageRepository);
    }

    @Test
    public void testAddLanguageValid() throws DuplicateKeyException, InvalidDataException {
        // Setup
        Language language = new Language();
        language.setLanguageId(1);
        language.setName("English");
        when(mockLanguageRepository.existsById(language.getLanguageId())).thenReturn(false);
        when(mockLanguageRepository.save(language)).thenReturn(language);

        // Test
        Language result = languageService.add(language);

        // Assertions
        assertNotNull(result);
        assertEquals(language.getLanguageId(), result.getLanguageId());
        assertEquals(language.getName(), result.getName());
    }

    @Test
    public void testAddLanguageDuplicateKey() throws DuplicateKeyException, InvalidDataException {
        // Setup
        Language language = new Language();
        language.setLanguageId(1);
        when(mockLanguageRepository.existsById(language.getLanguageId())).thenReturn(true);

        // Test and Assertions
        DuplicateKeyException thrown = assertThrows(DuplicateKeyException.class, () -> {
            languageService.add(language);
        });
        assertEquals("Ya existe un lenguaje con ese id", thrown.getMessage());
    }

    @Test
    public void testAddLanguageInvalid() {
        // Setup
        Language invalidLanguage = null;

        // Test and Assertions
        InvalidDataException thrown = assertThrows(InvalidDataException.class, () -> {
            languageService.add(invalidLanguage);
        });
        assertEquals("No se puede añadir un valor nulo", thrown.getMessage());
    }

    @Test
    public void testModifyLanguageValid() throws NotFoundException, InvalidDataException {
        // Setup
        Language language = new Language();
        language.setLanguageId(1);
        language.setName("Spanish");
        when(mockLanguageRepository.existsById(language.getLanguageId())).thenReturn(true);
        when(mockLanguageRepository.save(language)).thenReturn(language);

        // Test
        Language result = languageService.modify(language);

        // Assertions
        assertNotNull(result);
        assertEquals(language.getLanguageId(), result.getLanguageId());
    }

    @Test
    public void testModifyLanguageNotFound() {
        // Setup
        Language language = new Language();
        language.setLanguageId(1);
        when(mockLanguageRepository.existsById(language.getLanguageId())).thenReturn(false);

        // Test and Assertions
        NotFoundException thrown = assertThrows(NotFoundException.class, () -> {
            languageService.modify(language);
        });
        assertEquals("No existe un lenguaje con ese id", thrown.getMessage());
    }

    @Test
    public void testDeleteLanguageValid() throws InvalidDataException {
        // Setup
        Language language = new Language();
        language.setLanguageId(1);
        language.setName("French");
        when(mockLanguageRepository.existsById(language.getLanguageId())).thenReturn(true);

        // Test
        languageService.delete(language);

        // Verify interaction with the repository
        verify(mockLanguageRepository).delete(language);
    }

    @Test
    public void testDeleteLanguageInvalid() {
        // Setup
        Language invalidLanguage = new Language();
        invalidLanguage.setLanguageId(-1); // Invalid ID

        // Test and Assertions
        InvalidDataException thrown = assertThrows(InvalidDataException.class, () -> {
            languageService.delete(invalidLanguage);
        });
        assertEquals("No se puede añadir un valor nulo", thrown.getMessage());
    }

    @Test
    public void testDeleteById() {
        Integer languageId = 1;

        // Test
        languageService.deleteById(languageId);

        // Verify interaction with the repository
        verify(mockLanguageRepository).deleteById(languageId);
    }

    @Test
    public void testGetAllLanguages() {
        // Setup
        Language language = new Language();
        language.setLanguageId(1);
        language.setName("German");
        when(mockLanguageRepository.findAll()).thenReturn(List.of(language));

        // Test
        List<Language> languages = languageService.getAll();

        // Assertions
        assertFalse(languages.isEmpty());
        assertEquals(1, languages.size());
    }

    @Test
    public void testGetOneLanguage() {
        // Setup
        Language language = new Language();
        language.setLanguageId(1);
        language.setName("Chinese");
        when(mockLanguageRepository.findById(language.getLanguageId())).thenReturn(Optional.of(language));

        // Test
        Optional<Language> result = languageService.getOne(language.getLanguageId());

        // Assertions
        assertTrue(result.isPresent());
        assertEquals(language.getLanguageId(), result.get().getLanguageId());
    }

    @Test
    public void testGetOneLanguageNotFound() {
        // Setup
        Integer languageId = 1;
        when(mockLanguageRepository.findById(languageId)).thenReturn(Optional.empty());

        // Test
        Optional<Language> result = languageService.getOne(languageId);

        // Assertions
        assertFalse(result.isPresent());
    }
}
