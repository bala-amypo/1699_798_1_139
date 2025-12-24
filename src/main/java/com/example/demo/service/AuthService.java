package com.example.demo.service;

import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.RegisterRequest;
import com.example.demo.dto.AuthResponse;

public interface AuthService {
    AuthResponse login(LoginRequest loginRequest);   // must match exactly
    void register(RegisterRequest registerRequest);  // must match exactly
}
