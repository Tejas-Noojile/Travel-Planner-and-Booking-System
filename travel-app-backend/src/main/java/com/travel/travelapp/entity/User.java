package com.travel.travelapp.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class User {
    @Id
    @Column(name = "user_id", columnDefinition = "CHAR(36)")
    private String userId;
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
