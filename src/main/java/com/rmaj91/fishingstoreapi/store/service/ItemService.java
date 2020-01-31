package com.rmaj91.fishingstoreapi.store.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rmaj91.fishingstoreapi.store.model.Item;
import com.rmaj91.fishingstoreapi.store.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final ObjectMapper objectMapper;

    public Item create(Item item){
        return itemRepository.save(item);
    }

    public Item read(long id){
        Item item = itemRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No such item with this id"));
        return item;
    }

    public List<Item> readAll(){
        return itemRepository.findAll();
    }

    public Item update(Item item, long id){
        if(!itemRepository.existsById(id)) {
            throw new IllegalArgumentException("No such item with this id");
        }
        return itemRepository.save(item);
    }

    public Item patch(Map<String, Object> rodItemUpdates, long id) {
        Item itemToUpdate = itemRepository
                .findById(id)
                .orElseThrow(()->new IllegalArgumentException("No such item with this id"));
        Map rodItemToUpdateMap = objectMapper.convertValue(itemToUpdate,Map.class);
        rodItemUpdates.forEach(rodItemToUpdateMap::put);
        Item item = objectMapper.convertValue(rodItemToUpdateMap, Item.class);
        return itemRepository.save(item);

    }

    public void delete(long id){
        itemRepository.deleteById(id);
    }
}
