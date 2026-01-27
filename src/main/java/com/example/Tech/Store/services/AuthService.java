package com.example.Tech.Store.services;

import com.example.Tech.Store.entities.User;
import com.example.Tech.Store.repositories.ProductRepository;
import com.example.Tech.Store.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class AuthService {
    private final UserRepository userRepository;

    public User getLoggedUser() {
        var authentication=SecurityContextHolder.getContext().getAuthentication();
        var userId=(long)authentication.getPrincipal();
        return userRepository.findById(userId).orElseThrow(()->new RuntimeException("User not found"));

    }
}
