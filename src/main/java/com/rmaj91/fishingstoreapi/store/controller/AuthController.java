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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/v1/api/auth")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        return userService.findByEmail(user.getEmail())
                .<ResponseEntity<User>>map(u -> ResponseEntity.status(HttpStatus.CONFLICT).build())
                .orElseGet(() -> ResponseEntity.ok().body(userService.create(user)));
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody AuthRequest authRequest) {
        return userService.findByEmail(authRequest.getEmail())
                .filter(user -> passwordEncoder.matches(authRequest.getPassword(), user.getPassword()))
                .map(this::getJwtResponse)
                .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }

    private ResponseEntity<JwtResponse> getJwtResponse(User userByEmail) {
        String token = jwtUtil.generateToken(userByEmail);
        JwtResponse jwtResponse = JwtResponse.builder()
                .token(token)
                .expiredAt(jwtUtil.getAllClaimsFromToken(token).getExpiration())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(jwtResponse);
    }
}


