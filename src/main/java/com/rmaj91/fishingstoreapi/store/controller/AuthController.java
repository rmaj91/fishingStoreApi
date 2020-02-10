package com.rmaj91.fishingstoreapi.store.controller;

import com.rmaj91.fishingstoreapi.store.model.User;
import com.rmaj91.fishingstoreapi.store.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/v1/api/auth")
@AllArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> create(@RequestBody User user) {
        Optional<User> createdUser = Optional.empty();
        if (!userService.existByEmail(user.getEmail())) {
            createdUser = Optional.of(userService.create(user));
        }
        return createdUser.isPresent() ?
                ResponseEntity.status(HttpStatus.CREATED).body(createdUser.get()) : ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

}
