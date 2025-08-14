package com.travel.travelapp.dto;


import java.math.BigDecimal;
import java.time.Instant;

import com.travel.travelapp.entity.BookingStatus;

import lombok.Data;

@Data
public class BookingResponseDTO {
    private Long bookingId;
    private Integer userId;
    private Long hotelId;
    private Long flightId;
    private Long packageId;
    private BookingStatus status;
    private Instant bookingDate;
    private BigDecimal totalAmount;
    private Long paymentId; 
    private String message;
}