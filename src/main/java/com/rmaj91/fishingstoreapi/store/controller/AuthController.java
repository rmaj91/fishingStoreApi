package com.rmaj91.fishingstoreapi.store.controller;

import com.rmaj91.fishingstoreapi.security.AuthRequest;
import com.rmaj91.fishingstoreapi.security.JwtResponse;
import com.rmaj91.fishingstoreapi.security.JwtUtil;
import com.rmaj91.fishingstoreapi.store.model.User;
import com.rmaj91.fishingstoreapi.store.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Optional;

@RestController
@RequestMapping("/v1/api/auth")
@AllArgsConstructor
public class AuthController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        Optional<User> createdUser = Optional.empty();
        if (!userService.existByEmail(user.getEmail())) {
            createdUser = Optional.of(userService.create(user));
        }
        return createdUser.isPresent() ?
                ResponseEntity.status(HttpStatus.CREATED).body(createdUser.get()) : ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody AuthRequest authRequest) {
        Optional<User> userByEmail = userService.findByEmail(authRequest.getEmail());
        String requestPassword = authRequest.getPassword();
        ResponseEntity<JwtResponse> responseEntity;
        if (userByEmail.isPresent() && passwordEncoder.matches(requestPassword, userByEmail.get().getPassword())) {
            String token = jwtUtil.generateToken(userByEmail.get());

            JwtResponse jwtResponse = JwtResponse.builder()
                    .token(token)
                    .expiredAt(jwtUtil.getExpirationDateFromToken(token))
                    .build();
            responseEntity = ResponseEntity.status(HttpStatus.OK).body(jwtResponse);
        } else {
            responseEntity = ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return responseEntity;
    }

}


