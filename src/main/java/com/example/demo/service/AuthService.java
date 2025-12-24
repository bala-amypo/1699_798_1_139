package com.example.demo.service;

import com.example.demo.dto.RegisterRequest;

public interface AuthService {
    String register(RegisterRequest request); // Return token or user ID
    String login(String username, String password);
}
