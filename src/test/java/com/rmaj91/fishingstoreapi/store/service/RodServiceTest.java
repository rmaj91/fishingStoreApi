package com.rmaj91.fishingstoreapi.store.service;

import com.rmaj91.fishingstoreapi.store.model.Rod;
import com.rmaj91.fishingstoreapi.store.repository.RodRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
    void initAll(){
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
    void readAll(){
        // when
        rodService.readAll();

        // then
        verify(rodRepository,times(1)).findAll();
    }

    @Test
    void read(){
        // when
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, (() -> {
            rodService.read(1L);
        }));

        // then
        assertEquals(exception.getMessage(),"No such RodItem with this id");
        verify(rodRepository,times(1)).findById(1L);
    }

    @Test
    void whenUpdatesFail(){
        // when
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, (() -> {
            rodService.update(rod,1L);
        }));

        // then
        assertEquals(exception.getMessage(),"No such RodItem with this id");
    }

    @Test
    void whenUpdateSuccessful(){
        // given
        when(rodRepository.existsById(1L)).thenReturn(true);

        // when
        rodService.update(rod,1L);

        // then
        verify(rodRepository,times(1)).existsById(1L);
        verify(rodRepository,times(1)).save(rod);
    }

    @Test
    void whenPatchFail(){
        // when
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, (() -> {
            Map<String, Object> rodItemUpdates = new HashMap<>();
            rodService.patch(rodItemUpdates,1L);
        }));

        // then
        assertEquals(exception.getMessage(),"No such RodItem with this id");
        verify(rodRepository,times(1)).findById(1L);
    }

    @Disabled
    @Test
    void whenPatchSuccessful(){
    }

    @Test
    void delete() {
        // when
        rodService.delete(1L);

        // then
        verify(rodRepository,times(1)).deleteById(1L);
    }


}