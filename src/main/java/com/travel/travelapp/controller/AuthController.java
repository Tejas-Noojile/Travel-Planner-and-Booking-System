package com.travel.travelapp.controller;

import com.travel.travelapp.dto.AuthRequest;
import com.travel.travelapp.dto.SignupRequest;
import com.travel.travelapp.service.AuthService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    public void signup(@RequestBody SignupRequest request) {
    }

    @PostMapping("/login")
    public String login(@RequestBody AuthRequest request) {
        return null;
    }
}
