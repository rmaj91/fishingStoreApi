package com.rmaj91.fishingstoreapi.store.service;

import com.rmaj91.fishingstoreapi.store.model.Role;
import com.rmaj91.fishingstoreapi.store.model.User;
import com.rmaj91.fishingstoreapi.store.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public boolean existByEmail(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    public User create(User user){
        user.setRole(Role.USER);
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        return userRepository.save(user);
    }

    public Optional<User> findByEmail(String email){
        return userRepository.findByEmail(email);
    }
}
