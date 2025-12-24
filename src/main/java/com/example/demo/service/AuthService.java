package com.example.demo.service;

import com.example.demo.dto.RegisterRequest;

public interface AuthService {

    // Must return JWT token after login
    String login(String username, String password);

    // Must return JWT token after registration
    String register(RegisterRequest request);
}
