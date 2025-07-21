package com.travel.travelapp.dto;

import lombok.Data;

@Data
public class AuthRequest {
    private String emailOrPhone;
    private String password;
}
