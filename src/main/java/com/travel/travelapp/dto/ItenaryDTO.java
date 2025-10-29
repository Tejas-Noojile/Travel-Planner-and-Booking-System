package com.travel.travelapp.dto;

import lombok.Data;
import java.time.LocalTime;
import java.util.UUID;

@Data

public class ItenaryDTO {
    private UUID id;
    private String tripId;
    private int dayNumber;
    private LocalTime time;
    private String activityType;
    private String description;
    private String location;
}
