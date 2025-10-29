package com.travel.travelapp.entity;

import jakarta.persistence.*;
// import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalTime;

@Entity
@Table(name = "itinerary_items")
@Data
public class Itenary {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "item_id", columnDefinition = "CHAR(36)")
    // @NotNull(message = "Item ID cannot be null")
    private String itemId;

    @Column(name = "trip_id", nullable = false)
    // @NotNull(message = "Trip ID cannot be null")
    private String tripId;

    @Column(nullable = false)
    // @NotNull(message = "Day number cannot be null")
    private int dayNumber;

    @Column(nullable = false)
    private LocalTime time;

    private String activityType;
    private String description;
    private String location;
}