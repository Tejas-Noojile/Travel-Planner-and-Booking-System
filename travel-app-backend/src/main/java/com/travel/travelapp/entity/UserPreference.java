package com.travel.travelapp.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;

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
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private String travelInterests;
    private String budgetRange;
}