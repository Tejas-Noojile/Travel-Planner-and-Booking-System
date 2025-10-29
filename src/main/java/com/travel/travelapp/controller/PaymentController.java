package com.travel.travelapp.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.travel.travelapp.dto.CardFinalizationRequestDTO;
import com.travel.travelapp.dto.NetBankingFinalizationRequestDTO;
import com.travel.travelapp.dto.PaymentInitiationRequestDTO;
import com.travel.travelapp.dto.PaymentResponseDTO;
import com.travel.travelapp.dto.UpiFinalizationRequestDTO;
import com.travel.travelapp.entity.Payment;
import com.travel.travelapp.service.PaymentService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
@Slf4j
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("initiate")
    public ResponseEntity<PaymentResponseDTO> initiatePayment(@Valid @RequestBody PaymentInitiationRequestDTO initiationRequestDTO) {
        log.info("Received request to initiate payment for booking ID: {} ",initiationRequestDTO.getTripId());
        PaymentResponseDTO responseDTO = paymentService.initiatePayment(initiationRequestDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED); 
    }

    @PutMapping("user/{paymentId}/finalize/card") 
    public ResponseEntity<PaymentResponseDTO> finalizeCardPayment(@PathVariable Long paymentId,
                                                                  @Valid @RequestBody CardFinalizationRequestDTO request) {
        log.info("Received request to finalize card payment ID: {}", paymentId);
        PaymentResponseDTO responseDTO = paymentService.finalizeCardPayment(request,paymentId);
        return ResponseEntity.ok(responseDTO);
    }

    @PutMapping("user/{paymentId}/finalize/upi") 
    public ResponseEntity<PaymentResponseDTO> finalizeUpiPayment(@PathVariable Long paymentId,
                                                                 @Valid @RequestBody UpiFinalizationRequestDTO request) {
        log.info("Received request to finalize UPI payment ID: {}", paymentId);

        PaymentResponseDTO responseDTO = paymentService.finalizeUpiPayment(request,paymentId);
        return ResponseEntity.ok(responseDTO);
    }


    @PutMapping("user/{paymentId}/finalize/netbanking") 
    public ResponseEntity<PaymentResponseDTO> finalizeNetBankingPayment(@PathVariable Long paymentId,
                                                                        @Valid @RequestBody NetBankingFinalizationRequestDTO request) {
        log.info("Received request to finalize Net Banking payment ID: {}", paymentId);

        PaymentResponseDTO responseDTO = paymentService.finalizeNetBankingPayment(request,paymentId);
        return ResponseEntity.ok(responseDTO);
    }


    @GetMapping("user/{paymentId}")
    public ResponseEntity<PaymentResponseDTO> getPaymentDetails(@PathVariable Long paymentId) {
        log.info("Received request to get payment details for ID: {}", paymentId);
        PaymentResponseDTO responseDTO = paymentService.getPaymentDetails(paymentId);
        return ResponseEntity.ok(responseDTO);
    }
    
    @GetMapping("user/mypayments/{userId}")
    public ResponseEntity<List<Payment>> getMyPayments(@PathVariable String userId){
    	List<Payment> payments = paymentService.getMyPayments(userId);
    	if(payments.isEmpty()) {
    		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    	}
    	return ResponseEntity.ok(payments);
    }
}