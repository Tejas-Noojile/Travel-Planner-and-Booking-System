package com.travel.travelapp.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    private UUID userId;

    private String name;
    private String email;
    private String phone;
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String passportInfo;
    private Boolean isActive;
    private LocalDateTime createdAt;
}
