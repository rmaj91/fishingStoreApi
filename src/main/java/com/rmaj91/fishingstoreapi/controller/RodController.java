package com.rmaj91.fishingstoreapi.controller;

import com.rmaj91.fishingstoreapi.model.Rod;
import com.rmaj91.fishingstoreapi.repository.RodRespository;
import com.rmaj91.fishingstoreapi.service.RodService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Map;

@RestController
@RequestMapping("/v1/api/rods")
@RequiredArgsConstructor
public class RodController {

    private final RodService rodService;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Rod> create(@RequestBody Rod rod){
        Rod newRod = rodService.create(rod);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(newRod);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Rod>> readAll(){
        Collection<Rod> rods = rodService.readAll();
        return ResponseEntity
                .ok()
                .body(rods);
    }

    @GetMapping(path = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Rod> read(@PathVariable Long id){
        Rod book = rodService.read(id);
        return ResponseEntity
                .ok()
                .body(book);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        rodService.delete(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<Rod> update(@RequestBody Rod rod, @PathVariable Long id){
        Rod updatedRod = rodService.update(rod, id);
        return ResponseEntity
                .ok()
                .body(updatedRod);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Rod> patch(@RequestBody Map<String,String> rodUpdates, @PathVariable long id) {
        Rod updatedRod = rodService.patch(rodUpdates,id);
        return ResponseEntity
                .ok()
                .body(updatedRod);
    }
}
