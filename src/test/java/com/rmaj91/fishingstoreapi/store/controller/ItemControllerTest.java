package com.rmaj91.fishingstoreapi.store.controller;

import com.rmaj91.fishingstoreapi.store.model.Item;
import com.rmaj91.fishingstoreapi.store.service.ItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Map;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class ItemControllerTest {

    private Item item;
    @Mock
    private ItemService itemService;

    @InjectMocks
    private ItemController itemController;

    @BeforeEach
    void init() {
        item = new Item();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void readAll() {
        itemController.readAll();
        verify(itemService,times(1)).readAll();
    }

    @Test
    void read() {
        itemController.read(1L);
        verify(itemService,times(1)).read(1L);
    }

    @Test
    void create(){
        itemController.create(item);
        verify(itemService,times(1)).create(item);
    }

    @Test
    void update(){
        itemController.update(item,1L);
        verify(itemService,times(1)).update(item,1L);
    }

    @Test
    void patch(){
        Map map = Map.of();
        itemController.patch(map,1L);
        verify(itemService,times(1)).patch(map,1L);
    }

    @Test
    void delete(){
        itemController.delete(1L);
        verify(itemService,times(1)).delete(1L);
    }

}