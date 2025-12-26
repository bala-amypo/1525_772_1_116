package com.example.demo.service.impl;

import com.example.demo.dto.AuthRequest;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.JwtTokenProvider;
import com.example.demo.service.AuthService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
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

    // ✅ MUST MATCH INTERFACE EXACTLY
    @Override
    public String login(AuthRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        String rolesCsv = String.join(",", user.getRoles());

        return jwtTokenProvider.generateToken(
                user.getId(),
                user.getEmail(),
                rolesCsv
        );
    }

    // ✅ MUST MATCH INTERFACE EXACTLY
    @Override
    public String register(AuthRequest request) {

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRoles(request.getRoles());

        User savedUser = userRepository.save(user);

        String rolesCsv = String.join(",", savedUser.getRoles());

        return jwtTokenProvider.generateToken(
                savedUser.getId(),
                savedUser.getEmail(),
                rolesCsv
        );
    }
}
