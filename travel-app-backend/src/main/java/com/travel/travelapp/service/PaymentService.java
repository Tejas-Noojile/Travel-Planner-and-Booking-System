package com.travel.travelapp.service;

import java.util.List;

import com.travel.travelapp.dto.CardFinalizationRequestDTO;
import com.travel.travelapp.dto.NetBankingFinalizationRequestDTO;
import com.travel.travelapp.dto.PaymentInitiationRequestDTO;
import com.travel.travelapp.dto.PaymentResponseDTO;
import com.travel.travelapp.dto.UpiFinalizationRequestDTO;
import com.travel.travelapp.entity.Payment;


public interface PaymentService {
	
    PaymentResponseDTO initiatePayment(PaymentInitiationRequestDTO initiationRequest);
    PaymentResponseDTO finalizeCardPayment(CardFinalizationRequestDTO request,Long paymentId);
    PaymentResponseDTO finalizeUpiPayment(UpiFinalizationRequestDTO request,Long paymentId);
    PaymentResponseDTO finalizeNetBankingPayment(NetBankingFinalizationRequestDTO request,Long paymentId);
    PaymentResponseDTO getPaymentDetails(Long paymentId);   
    public List<Payment> getMyPayments(String userId);
    
}