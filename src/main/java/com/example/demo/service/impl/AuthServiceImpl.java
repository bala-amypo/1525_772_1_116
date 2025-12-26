package com.example.demo.service.impl;

import com.example.demo.dto.AuthRequest;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.JwtTokenProvider;
import com.example.demo.service.AuthService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Set;

@Service
@NoArgsConstructor
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private JwtTokenProvider jwtTokenProvider;

    @Override
    public String login(AuthRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        Set<String> roles = user.getRoles() == null
                ? Collections.emptySet()
                : user.getRoles();

        String rolesCsv = String.join(",", roles);

        return jwtTokenProvider.generateToken(
                user.getId(),
                user.getEmail(),
                rolesCsv
        );
    }

    @Override
    public String register(User user) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        if (user.getRoles() == null) {
            user.setRoles(Set.of("USER"));
        }

        User savedUser = userRepository.save(user);

        String rolesCsv = String.join(",", savedUser.getRoles());

        return jwtTokenProvider.generateToken(
                savedUser.getId(),
                savedUser.getEmail(),
                rolesCsv
        );
    }
}
