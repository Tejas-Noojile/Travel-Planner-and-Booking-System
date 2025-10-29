package com.travel.travelapp.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class PaymentInitiationRequestDTO {
    @NotNull(message = "Booking ID is required")
    
    private String tripId;

    @NotNull(message = "User ID is required")
    private String userId;

    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be positive")
    private Double amount;

}