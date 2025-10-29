package com.travel.travelapp.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Table(name = "trips")
@Data
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "trip_id", columnDefinition = "CHAR(36)")
    private String tripId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String destination;

    private LocalDate startDate;
    private LocalDate endDate;
    private int numPeople;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TripStatus status;

}