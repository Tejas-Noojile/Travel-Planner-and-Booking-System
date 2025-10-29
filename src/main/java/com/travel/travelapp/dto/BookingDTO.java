package com.travel.travelapp.dto;

import com.travel.travelapp.entity.BookingStatus;
import com.travel.travelapp.entity.BookingType;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class BookingDTO {
    private String bookingId;
    private String userId;
    private String tripId;
    private BookingType bookingType;
    private String vendorId;
    private String details; 
    private LocalDateTime startDatetime;
    private LocalDateTime endDatetime;
    private BookingStatus status;
    private double amount;
}
