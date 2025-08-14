package com.travel.travelapp.dto;


import lombok.Data;

@Data
public class PaymentResponseDTO {
    private Long paymentId;
    private String bookingId;
    private String userId;
    private Double amount;
    
}