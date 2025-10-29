package com.travel.travelapp.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalTime;

@Entity
@Table(name = "itinerary_items")
@Data
public class Itenary {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "item_id", columnDefinition = "CHAR(36)")
    private String itemId;

    @Column(name = "trip_id", nullable = false)
    private String tripId;

    @Column(nullable = false)
    private int dayNumber;

    @Column(nullable = false)
    private LocalTime time;

    private String activityType;
    private String description;
    private String location;
}