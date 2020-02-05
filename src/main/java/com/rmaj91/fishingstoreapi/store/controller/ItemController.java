package com.rmaj91.fishingstoreapi.store.controller;

import com.rmaj91.fishingstoreapi.store.model.Category;
import com.rmaj91.fishingstoreapi.store.model.Item;
import com.rmaj91.fishingstoreapi.store.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/api/items")
public class ItemController {

    private final ItemService itemService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Item>> readAll() {
        List<Item> items = itemService.readAll();
        return ResponseEntity
                .ok()
                .body(items);
    }

    @GetMapping(path = "/category/{category}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Item>> readAllByCategory(@PathVariable String category, @RequestParam int page,
                                                        @RequestParam int size) {
        List<Item> items = new ArrayList<>();
        Pageable pageable = PageRequest.of(page, size);
        for (Category cat : Category.values()) {
            if (cat.toString().toLowerCase().equals(category)) {
                items = itemService.readAllByCategory(cat, pageable);
                break;
            }
        }
        return ResponseEntity
                .ok()
                .body(items);
    }

    @GetMapping(path = "/category/all-categories", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Item>> readAllByCategory(@RequestParam int page, @RequestParam int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<Item> items = itemService.readAllPageable(pageable);
        return ResponseEntity
                .ok()
                .body(items);
    }

    @GetMapping(path = "/category/{category}/pages/{size}")
    public int getNumberOfPagesInCategory(@PathVariable String category, @PathVariable int size) {
        int pages = 1;
        for (Category cat : Category.values()) {
            if (cat.toString().toLowerCase().equals(category)) {
                pages = itemService.getNumberOfItemsInCategory(cat) / size + 1;
            }
        }
        return pages;
    }

    @GetMapping(path = "/category/all-categories/pages/{size}")
    public int getNumberOfPages(@PathVariable int size) {
        return itemService.readAll().size() / size + 1;
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Item> read(@PathVariable long id) {
        Item item = itemService.read(id);
        return ResponseEntity
                .ok()
                .body(item);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Item> create(@RequestBody Item item) {
        Item createdItem = itemService.create(item);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdItem);
    }

    @PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Item> update(@RequestBody Item item, @PathVariable long id) {
        Item updatedItem = itemService.update(item, id);
        return ResponseEntity
                .ok()
                .body(updatedItem);
    }

    @PatchMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Item> patch(@RequestBody Map<String, Object> rodItemUpdates, @PathVariable long id) {
        Item item = itemService.patch(rodItemUpdates, id);
        return ResponseEntity
                .ok()
                .body(item);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity delete(@PathVariable long id) {
        itemService.delete(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
