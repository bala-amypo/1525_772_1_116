package com.example.demo.service.impl;

import com.example.demo.dto.AuthRequest;
import com.example.demo.dto.AuthResponse;
import com.example.demo.dto.RegisterRequest;
import com.example.demo.entity.User;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.JwtTokenProvider;
import com.example.demo.service.AuthService;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public AuthServiceImpl() {
        this.userRepository = null;
        this.passwordEncoder = null;
        this.jwtTokenProvider = null;
    }

    @Override
    public AuthResponse register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Email already exists");
        }

        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(Set.of("ROLE_USER"))
                .active(true)
                .build();

        User saved = userRepository.save(user);

        String token = jwtTokenProvider.generateToken(
                saved.getId(),
                saved.getEmail(),
                saved.getRoles()
        );

        return new AuthResponse(
                token,
                saved.getId(),
                saved.getEmail(),
                saved.getRoles().iterator().next()
        );
    }

    @Override
    public AuthResponse login(AuthRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadRequestException("Invalid credentials");
        }

        String token = jwtTokenProvider.generateToken(
                user.getId(),
                user.getEmail(),
                user.getRoles()
        );

        return new AuthResponse(
                token,
                user.getId(),
                user.getEmail(),
                user.getRoles().iterator().next()
        );
    }
}
