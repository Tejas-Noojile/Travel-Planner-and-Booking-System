package com.travel.travelapp.service.impl;

import com.travel.travelapp.dto.AuthRequest;
import com.travel.travelapp.dto.SignupRequest;
import com.travel.travelapp.entity.Role;
import com.travel.travelapp.entity.User;
import com.travel.travelapp.exception.AuthenticationException;
import com.travel.travelapp.repository.UserRepository;
import com.travel.travelapp.security.JwtUtil;
import com.travel.travelapp.service.AuthService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public void signup(SignupRequest request) {
        User user = new User();
        user.setUserId(UUID.randomUUID().toString());
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.valueOf(request.getRole().toUpperCase()));
        user.setIsActive(true);
        user.setCreatedAt(LocalDateTime.now());

        userRepository.save(user);
    }

    @Override
    public String login(AuthRequest request) {
        Optional<User> userOpt = userRepository.findByEmail(request.getEmailOrPhone());
        if (userOpt.isEmpty()) {
            userOpt = userRepository.findByPhone(request.getEmailOrPhone());
        }

        User user = userOpt.orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new AuthenticationException("Invalid credentials");
        }

        return jwtUtil.generateToken(user);
    }
}
