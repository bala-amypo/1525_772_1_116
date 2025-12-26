package com.example.demo.service;

import com.example.demo.entity.User;

public interface AuthService {

    String login(String email, String password);

    String register(User user);
}
