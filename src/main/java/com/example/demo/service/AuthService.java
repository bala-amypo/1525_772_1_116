package com.example.demo.service;

import com.example.demo.dto.AuthRequest;
import com.example.demo.dto.JwtResponse;
import com.example.demo.dto.RegisterRequest;

public interface AuthService {

    JwtResponse register(RegisterRequest request);

    JwtResponse login(AuthRequest request);
}
