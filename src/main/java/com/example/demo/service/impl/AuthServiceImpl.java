package com.example.demo.service.impl;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.JwtTokenProvider;
import com.example.demo.service.AuthService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepo;
    private final PasswordEncoder encoder;
    private final JwtTokenProvider jwtProvider;

    public AuthServiceImpl(UserRepository userRepo,
                           PasswordEncoder encoder,
                           JwtTokenProvider jwtProvider) {
        this.userRepo = userRepo;
        this.encoder = encoder;
        this.jwtProvider = jwtProvider;
    }

    @Override
    public String register(String email, String password) {

        if (userRepo.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("User exists");
        }

        User user = new User();
        user.setEmail(email);
        user.setPassword(encoder.encode(password));
        user.setRoles(Set.of("ROLE_ADVISOR")); // ✅ CORRECT

        userRepo.save(user);

        return "User registered";
    }

    @Override
    public String login(String email, String password) {

        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!encoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        return jwtProvider.createToken(
                user.getId(),
                user.getEmail(),
                user.getRoles()
        );
    }
}
