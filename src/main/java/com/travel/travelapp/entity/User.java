package com.travel.travelapp.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.*;

@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    @Column(name = "user_id", columnDefinition = "CHAR(36)")
    private String userId;

    private String name;
    private String email;
    private String phone;
    private String passwordHash;

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getPassportInfo() {
        return passportInfo;
    }

    public void setPassportInfo(String passportInfo) {
        this.passportInfo = passportInfo;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Enumerated(EnumType.STRING)
    private Role role;

    private String passportInfo;
    private Boolean isActive;
    private LocalDateTime createdAt;

    public void setIsActive(boolean b) {
    }
}
