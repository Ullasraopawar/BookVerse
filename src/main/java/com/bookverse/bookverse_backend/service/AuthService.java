package com.bookverse.bookverse_backend.service;

import com.bookverse.bookverse_backend.dto.AuthResponse;
import com.bookverse.bookverse_backend.dto.LoginRequest;
import com.bookverse.bookverse_backend.dto.RegisterRequest;

public interface AuthService {

    AuthResponse register(RegisterRequest request);

    AuthResponse login(LoginRequest request);

}