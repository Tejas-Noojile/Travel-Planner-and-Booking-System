package com.travel.travelapp.dto;

import lombok.Data;

@Data
public class AuthRequest {
    private String emailOrPhone;

    public String getEmailOrPhone() {
        return emailOrPhone;
    }

    public String getPassword() {
        return password;
    }

    public void setEmailOrPhone(String emailOrPhone) {
        this.emailOrPhone = emailOrPhone;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private String password;

}
