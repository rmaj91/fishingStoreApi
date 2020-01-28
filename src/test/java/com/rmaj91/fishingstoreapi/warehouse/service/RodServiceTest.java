package com.rmaj91.fishingstoreapi.warehouse.service;

import com.rmaj91.fishingstoreapi.warehouse.model.Rod;
import com.rmaj91.fishingstoreapi.warehouse.repository.RodRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class RodServiceTest {

    private Rod rod;
    @Mock
    private RodRepository rodRepository;

    @InjectMocks
    private RodService rodService;

    @BeforeEach
    void init() {
        rod = new Rod();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void create(){
        // when
        rodService.create(rod);

        // then
        verify(rodRepository,times(1)).save(rod);
    }

    @Test
    void readAll() {
        // when
        rodService.readAll();

        // then
        verify(rodRepository,times(1)).findAll();
    }

    @Test
    void read() {
        // when
        // then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            rodService.read(1L);
        });
        assertEquals(exception.getMessage(),"Rod with exact id not found");
        verify(rodRepository,times(1)).findById(1L);
    }

    @Test
    void delete() {
        // when
        rodService.delete(1L);

        // then
        verify(rodRepository,times(1)).deleteById(1L);
    }

    @Test
    void whenUpdateFail() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            rodService.update(rod, 1L);
        });
        assertEquals(exception.getMessage(),"Rod with exact id not found");
        verify(rodRepository,times(1)).findById(1L);
    }

    @Test
    void whenUpdateSuccessful(){
        // given
        when(rodRepository.findById(1L)).thenReturn(Optional.of(rod));

        // when
        rodService.update(rod,1L);

        // then
        verify(rodRepository,times(1)).findById(1L);
        verify(rodRepository,times(1)).save(rod);
    }

    @Test
    void patchFail() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            Map<String, String> rodUpdates = new HashMap<>();
            rodService.patch(rodUpdates,1L);
        });
        assertEquals(exception.getMessage(),"Rod with exact id not found");
        verify(rodRepository,times(1)).findById(1L);
    }

    @Disabled
    @Test
    void patchSuccessful() {

    }

}