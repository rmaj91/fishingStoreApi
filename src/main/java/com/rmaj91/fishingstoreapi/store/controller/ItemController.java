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
import org.springframework.web.bind.annotation.CrossOrigin;
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
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/api/items")
@CrossOrigin(origins = "http://localhost:3000")
public class ItemController {

    private final ItemService itemService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Item>> readAll() {
        List<Item> items = itemService.readAll();
        return items.isEmpty() ?
                ResponseEntity.status(HttpStatus.NOT_FOUND).build() : ResponseEntity.ok().body(items);
    }

    @GetMapping(path = "/category/{category}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Item>> readAllByCategory(@PathVariable String category, @RequestParam int page, @RequestParam int size) {
        List<Item> items = new ArrayList<>();
        Pageable pageable = PageRequest.of(page, size);

        return Arrays.stream(Category.values())
                .filter(cat -> cat.toString().toLowerCase().equals(category))
                .findFirst()
                .map(cos -> itemService.readAllByCategory(cos, pageable))
                .map(list -> ResponseEntity.ok().body(list))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping(path = "/category/all-categories", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Item>> readAllByCategory(@RequestParam int page, @RequestParam int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<Item> items = itemService.readAll(pageable);
        return items.isEmpty() ?
                ResponseEntity.status(HttpStatus.NOT_FOUND).build() : ResponseEntity.ok().body(items);
    }

    @GetMapping(path = "/category/{category}/pages/{size}")
    public int getNumberOfPagesInCategory(@PathVariable String category, @PathVariable int size) {
        return Arrays.stream(Category.values())
                .filter(cat -> cat.toString().toLowerCase().equals(category))
                .map(cat -> getNumberOfPages(cat, size))
                .findFirst()
                .get();
    }

    @GetMapping(path = "/category/all-categories/pages/{size}")
    public int getNumberOfPagesInAllCategories(@PathVariable int size) {
        return itemService.readAll().size() / size + 1;
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Item> read(@PathVariable long id) {
        return itemService.read(id)
                .map(value -> ResponseEntity.ok().body(value))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
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
        return itemService.update(item, id)
                .map(value -> ResponseEntity.ok().body(value))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PatchMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Item> patch(@RequestBody Map<String, Object> rodItemUpdates, @PathVariable long id) {
        return itemService.patch(rodItemUpdates, id)
                .map(item -> ResponseEntity.ok().body(item))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> delete(@PathVariable long id) {
        return itemService.delete(id) ?
                ResponseEntity.status(HttpStatus.NO_CONTENT).build() : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    private int getNumberOfPages(Category cat, int size) {
        return itemService.getNumberOfItemsInCategory(cat) / size + 1;
    }
}
