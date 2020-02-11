package com.rmaj91.fishingstoreapi.store.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rmaj91.fishingstoreapi.store.model.Category;
import com.rmaj91.fishingstoreapi.store.model.Item;
import com.rmaj91.fishingstoreapi.store.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final ObjectMapper objectMapper;

    public Item create(Item item) {
        return itemRepository.save(item);
    }

    public Item read(long id) {
        Item item = itemRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No such item with this id"));
        return item;
    }

    public List<Item> readAll() {
        List<Item> items = new ArrayList<>();
        itemRepository.findAll().forEach(items::add);
        return items;
    }

    public Optional<Item> update(Item item, long id) {
        Optional<Item> updatedItem = Optional.empty();
        if (itemRepository.existsById(id)) {
            updatedItem = Optional.of(itemRepository.save(item));
        }
        return updatedItem;
    }

    public Optional<Item> patch(Map<String, Object> rodItemUpdates, long id) {
        Optional<Item> itemToUpdate = itemRepository.findById(id);
        if (itemToUpdate.isPresent()) {
            Map rodItemToUpdateMap = objectMapper.convertValue(itemToUpdate.get(), Map.class);
            rodItemUpdates.forEach(rodItemToUpdateMap::put);
            Item item = objectMapper.convertValue(rodItemToUpdateMap, Item.class);
            itemToUpdate = Optional.of(itemRepository.save(item));
        }
        return itemToUpdate;
    }

    public boolean delete(long id) {
        boolean deleted = false;
        if (itemRepository.findById(id).isPresent()) {
            itemRepository.deleteById(id);
            deleted = true;
        }
        return deleted;
    }

    public List<Item> readAll(Pageable pageable) {
        return itemRepository.findAll(pageable).toList();
    }

    public List<Item> readAllByCategory(Category category, Pageable pageable) {
        return itemRepository.findByCategory(category, pageable);
    }

    public int getNumberOfItemsInCategory(Category category) {
        List<Item> items = new ArrayList<>();
        itemRepository.findByCategory(category).forEach(items::add);
        return items.size();
    }
}
