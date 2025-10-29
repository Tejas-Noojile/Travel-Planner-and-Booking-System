package com.travel.travelapp.dto;


import lombok.Data;

@Data
public class PaymentResponseDTO {
    private Long paymentId;
    private String tripId;
    private String userId;
    private Double amount;
    
}