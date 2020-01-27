package com.rmaj91.fishingstoreapi.store.controller;

import com.rmaj91.fishingstoreapi.store.model.RodItem;
import com.rmaj91.fishingstoreapi.store.service.RodItemService;
import com.rmaj91.fishingstoreapi.warehouse.model.Rod;
import lombok.RequiredArgsConstructor;
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
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/api/rodItems")
public class RodItemController {

    private final RodItemService rodItemService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<RodItem>> readAll() {
        List<RodItem> rodItems = rodItemService.readAll();
        return ResponseEntity
                .ok()
                .body(rodItems);
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RodItem> read(@PathVariable long id) {
        RodItem rodItem = rodItemService.read(id);
        return ResponseEntity
                .ok()
                .body(rodItem);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RodItem> create(@RequestBody RodItem rodItem) {
        RodItem createdRodItem = rodItemService.create(rodItem);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdRodItem);
    }

    @PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RodItem> update(@RequestBody RodItem rodItem, @PathVariable long id) {
        RodItem updatedRodItem = rodItemService.update(rodItem, id);
        return ResponseEntity
                .ok()
                .body(updatedRodItem);
    }

    @PatchMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RodItem> patch(@RequestBody Map<String, Object> rodItemUpdates, @PathVariable long id) {
        RodItem rodItem = rodItemService.patch(rodItemUpdates, id);
        return ResponseEntity
                .ok()
                .body(rodItem);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity delete(@PathVariable long id){
        rodItemService.delete(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
