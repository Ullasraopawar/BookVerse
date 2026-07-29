package com.bookverse.bookverse_backend.controller;

import com.bookverse.bookverse_backend.dto.AuthResponse;
import com.bookverse.bookverse_backend.dto.LoginRequest;
import com.bookverse.bookverse_backend.dto.RegisterRequest;
import com.bookverse.bookverse_backend.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public AuthResponse register(@RequestBody RegisterRequest request) {
        return authService.register(request);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }
}