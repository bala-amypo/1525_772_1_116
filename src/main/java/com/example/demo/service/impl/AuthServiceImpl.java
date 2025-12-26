package com.example.demo.service.impl;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.JwtTokenProvider;
import com.example.demo.service.AuthService;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor
public class AuthServiceImpl implements AuthService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private JwtTokenProvider jwtTokenProvider;

    public AuthServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public String login(String email, String password) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        String rolesCsv = user.getRoles();   // now stored as String

        return jwtTokenProvider.generateToken(
                user.getId(),
                user.getEmail(),
                rolesCsv
        );
    }

    @Override
    public String register(User user) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        User savedUser = userRepository.save(user);

        return jwtTokenProvider.generateToken(
                savedUser.getId(),
                savedUser.getEmail(),
                savedUser.getRoles()
        );
    }
}
