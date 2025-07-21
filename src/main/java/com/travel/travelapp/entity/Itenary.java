package com.travel.travelapp.entity;

import jakarta.persistence.*;
import java.time.LocalTime;
import java.util.UUID;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "itinerary_items")
@Data
@NoArgsConstructor
public class Itenary {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private UUID tripId;

    @Column(nullable = false)
    private int dayNumber;

    @Column(nullable = false)
    private LocalTime time;

    @Column
    private String activityType;

    @Column
    private String description;

    @Column
    private String location;
}