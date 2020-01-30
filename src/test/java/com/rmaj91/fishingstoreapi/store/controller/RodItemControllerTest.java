package com.rmaj91.fishingstoreapi.store.controller;

import com.rmaj91.fishingstoreapi.store.model.RodItem;
import com.rmaj91.fishingstoreapi.store.service.RodItemService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class RodItemControllerTest {

    private RodItem rodItem;
    @Mock
    private RodItemService rodItemService;

    @InjectMocks
    private RodItemController rodItemController;

    @BeforeEach
    void init() {
        rodItem = new RodItem();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void readAll() {
        rodItemController.readAll();
        verify(rodItemService,times(1)).readAll();
    }

    @Test
    void read() {
        rodItemController.read(1L);
        verify(rodItemService,times(1)).read(1L);
    }

    @Test
    void create(){
        rodItemController.create(rodItem);
        verify(rodItemService,times(1)).create(rodItem);
    }

    @Test
    void update(){
        rodItemController.update(rodItem,1L);
        verify(rodItemService,times(1)).update(rodItem,1L);
    }

    @Test
    void patch(){
        Map map = Map.of();
        rodItemController.patch(map,1L);
        verify(rodItemService,times(1)).patch(map,1L);
    }

    @Test
    void delete(){
        rodItemController.delete(1L);
        verify(rodItemService,times(1)).delete(1L);
    }

}