package com.example.demo.service;

import com.example.demo.dto.AuthRequest;
import com.example.demo.dto.RegisterRequest;

public interface AuthService {

    String login(AuthRequest request);

    String register(RegisterRequest request);
}
