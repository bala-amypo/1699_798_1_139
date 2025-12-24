package com.example.app.service;

import com.example.app.dto.LoginRequest;
import com.example.app.dto.RegisterRequest;
import com.example.app.dto.AuthResponse;

public interface AuthService {
    AuthResponse register(RegisterRequest request);
    AuthResponse login(LoginRequest request);
   
}
