package com.travel.travelapp.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

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

    @ManyToOne
    @JoinColumn(name = "trip_id", nullable = false) 
    @NotNull(message = "Trip is required")
    private Trip trip;

    @NotNull(message = "Day number is required")
    @Column(nullable = false)
    private int dayNumber;

    @NotNull(message = "Time is required")
    @Column(nullable = false)
    private LocalTime time;

    @Column
    private String activityType;

    @Column
    private String description;

    @Column
    private String location;
}