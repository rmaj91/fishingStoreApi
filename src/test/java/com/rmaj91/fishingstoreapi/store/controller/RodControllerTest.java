package com.rmaj91.fishingstoreapi.store.controller;

import com.rmaj91.fishingstoreapi.store.model.Rod;
import com.rmaj91.fishingstoreapi.store.service.RodService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Map;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class RodControllerTest {

    private Rod rod;
    @Mock
    private RodService rodService;

    @InjectMocks
    private RodController rodController;

    @BeforeEach
    void init() {
        rod = new Rod();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void readAll() {
        rodController.readAll();
        verify(rodService,times(1)).readAll();
    }

    @Test
    void read() {
        rodController.read(1L);
        verify(rodService,times(1)).read(1L);
    }

    @Test
    void create(){
        rodController.create(rod);
        verify(rodService,times(1)).create(rod);
    }

    @Test
    void update(){
        rodController.update(rod,1L);
        verify(rodService,times(1)).update(rod,1L);
    }

    @Test
    void patch(){
        Map map = Map.of();
        rodController.patch(map,1L);
        verify(rodService,times(1)).patch(map,1L);
    }

    @Test
    void delete(){
        rodController.delete(1L);
        verify(rodService,times(1)).delete(1L);
    }

}