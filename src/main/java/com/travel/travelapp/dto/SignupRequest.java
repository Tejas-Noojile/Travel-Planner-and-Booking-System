package com.travel.travelapp.dto;

import lombok.Data;

@Data
public class SignupRequest {
    private String name;
    private String email;
    private String phone;
    private String password;
    private String role;
}
