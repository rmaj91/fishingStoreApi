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

    public Optional<Item> read(long id) {
        return itemRepository.findById(id);
    }

    public List<Item> readAll() {
        List<Item> items = new ArrayList<>();
        itemRepository.findAll().forEach(items::add);
        return items;
    }

    public Optional<Item> update(Item item, long id) {
        return itemRepository.findById(id)
                .map(it -> itemRepository.save(item));
    }

    public Optional<Item> patch(Map<String, Object> rodItemUpdates, long id) {
        return itemRepository.findById(id)
                .map(itemToPatch -> patchItem(itemToPatch, rodItemUpdates));
    }

    public boolean delete(long id) {
        return itemRepository.findById(id)
                .map(user -> {
                    itemRepository.delete(user);
                    return true;
                })
                .orElseGet(() -> false);
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

    private Item patchItem(Item itemToPatch, Map<String, Object> rodItemUpdates) {
        Map rodItemToUpdateMap = objectMapper.convertValue(itemToPatch, Map.class);
        rodItemUpdates.forEach(rodItemToUpdateMap::put);
        Item item = objectMapper.convertValue(rodItemToUpdateMap, Item.class);
        return itemRepository.save(item);
    }
}
