package com.travel.travelapp.dto;

import lombok.Data;
import java.time.LocalDate;

import com.travel.travelapp.entity.TripStatus;

@Data
public class TripDTO {
    private String tripId;
    private String title;
    private String destination;
    private LocalDate startDate;
    private LocalDate endDate;
    private int numPeople;
    private String status;
}
