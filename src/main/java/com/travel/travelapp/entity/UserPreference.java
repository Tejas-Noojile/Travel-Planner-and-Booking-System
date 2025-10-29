package com.travel.travelapp.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "userpreferences")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class UserPreference {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID preferenceId;
    @OneToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;
    private String travelInterests;
    private String budgetRange;
}