package com.rmaj91.fishingstoreapi.store.controller;

import com.rmaj91.fishingstoreapi.store.model.Rod;
import com.rmaj91.fishingstoreapi.store.service.RodService;
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
public class RodController {

    private final RodService rodService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Rod>> readAll() {
        List<Rod> rods = rodService.readAll();
        return ResponseEntity
                .ok()
                .body(rods);
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Rod> read(@PathVariable long id) {
        Rod rod = rodService.read(id);
        return ResponseEntity
                .ok()
                .body(rod);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Rod> create(@RequestBody Rod rod) {
        Rod createdRod = rodService.create(rod);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdRod);
    }

    @PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Rod> update(@RequestBody Rod rod, @PathVariable long id) {
        Rod updatedRod = rodService.update(rod, id);
        return ResponseEntity
                .ok()
                .body(updatedRod);
    }

    @PatchMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Rod> patch(@RequestBody Map<String, Object> rodItemUpdates, @PathVariable long id) {
        Rod rod = rodService.patch(rodItemUpdates, id);
        return ResponseEntity
                .ok()
                .body(rod);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity delete(@PathVariable long id){
        rodService.delete(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
