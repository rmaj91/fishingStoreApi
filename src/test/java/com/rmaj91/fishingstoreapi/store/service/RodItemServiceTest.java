package com.rmaj91.fishingstoreapi.store.service;

import com.rmaj91.fishingstoreapi.store.model.RodItem;
import com.rmaj91.fishingstoreapi.store.repository.RodItemRepository;
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

class RodItemServiceTest {

    private RodItem rodItem;

    @Mock
    private RodItemRepository rodItemRepository;

    @InjectMocks
    private RodItemService rodItemService;

    @BeforeEach
    void initAll(){
        rodItem = new RodItem();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void create(){
        // when
        rodItemService.create(rodItem);

        // then
        verify(rodItemRepository,times(1)).save(rodItem);
    }

    @Test
    void readAll(){
        // when
        rodItemService.readAll();

        // then
        verify(rodItemRepository,times(1)).findAll();
    }

    @Test
    void read(){
        // when
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, (() -> {
            rodItemService.read(1L);
        }));

        // then
        assertEquals(exception.getMessage(),"No such RodItem with this id");
        verify(rodItemRepository,times(1)).findById(1L);
    }

    @Test
    void whenUpdatesFail(){
        // when
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, (() -> {
            rodItemService.update(rodItem,1L);
        }));

        // then
        assertEquals(exception.getMessage(),"No such RodItem with this id");
    }

    @Test
    void whenUpdateSuccessful(){
        // given
        when(rodItemRepository.existsById(1L)).thenReturn(true);

        // when
        rodItemService.update(rodItem,1L);

        // then
        verify(rodItemRepository,times(1)).existsById(1L);
        verify(rodItemRepository,times(1)).save(rodItem);
    }

    @Test
    void whenPatchFail(){
        // when
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, (() -> {
            Map<String, Object> rodItemUpdates = new HashMap<>();
            rodItemService.patch(rodItemUpdates,1L);
        }));

        // then
        assertEquals(exception.getMessage(),"No such RodItem with this id");
        verify(rodItemRepository,times(1)).findById(1L);
    }

    @Disabled
    @Test
    void whenPatchSuccessful(){
    }

    @Test
    void delete() {
        // when
        rodItemService.delete(1L);

        // then
        verify(rodItemRepository,times(1)).deleteById(1L);
    }


}