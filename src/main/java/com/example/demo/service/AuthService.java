package com.example.demo.service;

import com.example.demo.dto.RegisterRequest;
import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.AuthResponse;

public interface AuthService {
    String register(RegisterRequest request); // return token
    String login(String username, String password); // return token
}
