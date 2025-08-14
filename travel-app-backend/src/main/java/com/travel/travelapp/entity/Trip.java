package com.travel.travelapp.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "trips")
public class Trip {

    @Id
    @Column(name = "trip_id", columnDefinition = "CHAR(36)")
    private String tripId = UUID.randomUUID().toString();

    @Column(name = "user_id", nullable = false, columnDefinition = "CHAR(36)")
    private String userId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String destination;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "num_people")
    private Integer numPeople;

    @Enumerated(EnumType.STRING)
    private TripStatus status = TripStatus.DRAFT;

    // Getters & setters
    public String getTripId() { return tripId; }
    public void setTripId(String tripId) { this.tripId = tripId; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDestination() { return destination; }
    public void setDestination(String destination) { this.destination = destination; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

    public Integer getNumPeople() { return numPeople; }
    public void setNumPeople(Integer numPeople) { this.numPeople = numPeople; }

    public TripStatus getStatus() { return status; }
    public void setStatus(TripStatus status) { this.status = status; }
}
