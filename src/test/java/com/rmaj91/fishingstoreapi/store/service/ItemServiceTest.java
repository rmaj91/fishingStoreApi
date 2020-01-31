package com.rmaj91.fishingstoreapi.store.service;

import com.rmaj91.fishingstoreapi.store.model.Item;
import com.rmaj91.fishingstoreapi.store.repository.ItemRepository;
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

class ItemServiceTest {

    private Item item;

    @Mock
    private ItemRepository itemRepository;

    @InjectMocks
    private ItemService itemService;

    @BeforeEach
    void initAll(){
        item = new Item();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void create(){
        // when
        itemService.create(item);

        // then
        verify(itemRepository,times(1)).save(item);
    }

    @Test
    void readAll(){
        // when
        itemService.readAll();

        // then
        verify(itemRepository,times(1)).findAll();
    }

    @Test
    void read(){
        // when
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, (() -> {
            itemService.read(1L);
        }));

        // then
        assertEquals(exception.getMessage(),"No such item with this id");
        verify(itemRepository,times(1)).findById(1L);
    }

    @Test
    void whenUpdatesFail(){
        // when
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, (() -> {
            itemService.update(item,1L);
        }));

        // then
        assertEquals(exception.getMessage(),"No such item with this id");
    }

    @Test
    void whenUpdateSuccessful(){
        // given
        when(itemRepository.existsById(1L)).thenReturn(true);

        // when
        itemService.update(item,1L);

        // then
        verify(itemRepository,times(1)).existsById(1L);
        verify(itemRepository,times(1)).save(item);
    }

    @Test
    void whenPatchFail(){
        // when
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, (() -> {
            Map<String, Object> rodItemUpdates = new HashMap<>();
            itemService.patch(rodItemUpdates,1L);
        }));

        // then
        assertEquals(exception.getMessage(),"No such item with this id");
        verify(itemRepository,times(1)).findById(1L);
    }

    @Disabled
    @Test
    void whenPatchSuccessful(){
    }

    @Test
    void delete() {
        // when
        itemService.delete(1L);

        // then
        verify(itemRepository,times(1)).deleteById(1L);
    }


}