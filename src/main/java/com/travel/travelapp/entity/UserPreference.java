package com.travel.travelapp.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Table(name = "userpreferences")
@Data
public class UserPreference {
    @Id
    private UUID preferenceId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String travelInterests;
    private String budgetRange;
}
