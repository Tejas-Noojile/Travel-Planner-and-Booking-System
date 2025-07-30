package com.travel.travelapp.service;

import com.travel.travelapp.dto.AuthRequest;
import com.travel.travelapp.dto.SignupRequest;
public interface AuthService {
    void signup(SignupRequest request);
    String login(AuthRequest request);
}
