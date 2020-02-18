package com.rmaj91.fishingstoreapi.store.service;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ItemServiceTest {

    private Item item;

    @Mock
    private ItemRepository itemRepository;
    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private ItemService itemService;

    @BeforeEach
    void initAll(){
        item = new Item();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void create(){
        itemService.create(item);

        verify(itemRepository,times(1)).save(item);
    }

    @Test
    void readAll(){
        itemService.readAll();

        verify(itemRepository,times(1)).findAll();
    }

    @Test
    void read(){
        itemService.read(1L);

        verify(itemRepository,times(1)).findById(1L);
    }

    @Test
    void whenUpdatesFail(){
        when(itemRepository.findById(1L)).thenReturn(Optional.empty());
        Optional<Item> updatedItem = itemService.update(item, 1L);

        assertTrue(updatedItem.isEmpty());
    }

    @Test
    void whenUpdateSuccessful(){
        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));
        when(itemRepository.save(item)).thenReturn(item);
        Optional<Item> updatedItem = itemService.update(item, 1L);

        verify(itemRepository,times(1)).save(item);
        assertTrue(updatedItem.isPresent());
    }

    @Test
    void whenPatchFail(){
        Map<String, Object> map = new HashMap<>();

        when(itemRepository.findById(1L)).thenReturn(Optional.empty());
        Optional<Item> item = itemService.patch(map, 1L);

        assertTrue(item.isEmpty());
    }

    @Test
    void whenPatchSuccessful(){
        Map<String, Object> map = new HashMap<>();

        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));
        when(objectMapper.convertValue(item,Map.class)).thenReturn(map);
        when(objectMapper.convertValue(map,Item.class)).thenReturn(item);
        when(itemRepository.save(item)).thenReturn(item);
        Optional<Item> patchedItem = itemService.patch(map, 1L);

        assertTrue(patchedItem.isPresent());
    }

    @Test
    void whenDeleteThenTrue() {
        Item item = new Item();
        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));
        boolean isItemDeleted = itemService.delete(1L);

        verify(itemRepository,times(1)).findById(1L);
        verify(itemRepository,times(1)).delete(item);
        assertTrue(isItemDeleted);
    }

    @Test
    void whenDeleteThenFalse() {
        when(itemRepository.findById(1L)).thenReturn(Optional.empty());
        boolean isItemDeleted = itemService.delete(1L);

        verify(itemRepository,times(1)).findById(1L);
        assertFalse(isItemDeleted);
    }


}