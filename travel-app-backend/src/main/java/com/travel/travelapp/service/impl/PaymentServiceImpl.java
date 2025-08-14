package com.travel.travelapp.service.impl;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.travel.travelapp.dto.CardFinalizationRequestDTO;
import com.travel.travelapp.dto.NetBankingFinalizationRequestDTO;
import com.travel.travelapp.dto.PaymentInitiationRequestDTO;
import com.travel.travelapp.dto.PaymentResponseDTO; 
import com.travel.travelapp.dto.UpiFinalizationRequestDTO;
import com.travel.travelapp.exception.BadRequestException;
import com.travel.travelapp.exception.PaymentProcessingException;
import com.travel.travelapp.exception.ResourceNotFoundException;
import com.travel.travelapp.entity.Payment;
import com.travel.travelapp.entity.Booking;
import com.travel.travelapp.entity.BookingStatus;
import com.travel.travelapp.entity.PaymentMethod;
import com.travel.travelapp.entity.PaymentStatus;
import com.travel.travelapp.repository.BookingRepository;
import com.travel.travelapp.repository.PaymentRepository;
import com.travel.travelapp.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final BookingRepository bookingRepository;

    @Override
    @Transactional
    public PaymentResponseDTO initiatePayment(PaymentInitiationRequestDTO initiationRequest) {
        log.info("Initiating payment for booking ID: {}", initiationRequest.getBookingId());

       
            Payment existingPayment = paymentRepository.findByBookingId(initiationRequest.getBookingId())
                .orElse(null);
            if (existingPayment != null && (existingPayment.getPaymentStatus() == PaymentStatus.SUCCESS || existingPayment.getPaymentStatus() == PaymentStatus.PENDING)) {
                throw new BadRequestException("Payment is already " + existingPayment.getPaymentStatus() + " for booking ID: " + initiationRequest.getBookingId());
            }
        

        Payment payment = new Payment();
        payment.setBookingId(initiationRequest.getBookingId());
        payment.setUserId(initiationRequest.getUserId());
        payment.setAmount(initiationRequest.getAmount());
        payment.setCurrency("INR");
        payment.setPaymentStatus(PaymentStatus.PENDING);
        payment.setPaymentTimestamp(Instant.now());

        Payment savedPayment = paymentRepository.save(payment);
        

        log.info("Payment initiated successfully with ID: {} for booking ID: {}", savedPayment.getPaymentId(), initiationRequest.getBookingId());
        return mapToPaymentResponseDTO(savedPayment, "Payment initiated. Please provide details to finalize.");
    }

    @Override
    @Transactional
    public PaymentResponseDTO finalizeCardPayment(CardFinalizationRequestDTO request,Long paymentId) {
        log.info("Attempting to finalize card payment ID: {}",paymentId);

        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found with ID: " + paymentId));

        if (payment.getPaymentStatus() != PaymentStatus.PENDING) {
            throw new BadRequestException("Payment with ID " + paymentId + " cannot be finalized. Current status: " + payment.getPaymentStatus());
        }

        if (payment.getPaymentMethod() != null) {

            throw new BadRequestException("Payment with ID " + paymentId + " has already been finalized with method: " + payment.getPaymentMethod());
        }
        payment.setPaymentMethod(PaymentMethod.CREDIT_CARD);


        

        String transactionId = generateUniqueTransactionId();
        PaymentStatus paymentStatus = PaymentStatus.SUCCESS;
        String errorMessage = null;

        try {
            log.info("Processing card payment ending in: {}", request.getCardNumber().substring(request.getCardNumber().length() - 4));
            if (request.getCardNumber().endsWith("0000")) {
                throw new PaymentProcessingException("Simulated card processing failure due to card ending in '0000'.");
            }
            payment.setCardLastFourDigits(request.getCardNumber().substring(request.getCardNumber().length() - 4));
            payment.setCardType(deriveCardType(request.getCardNumber()));

            log.info("Payment gateway processing successful for payment ID: {}", paymentId);
        } catch (PaymentProcessingException | BadRequestException e) {
            paymentStatus = PaymentStatus.FAILED;
            errorMessage = e.getMessage();
            log.error("Payment finalization failed for payment ID {}: {}", paymentId, e.getMessage());
            throw e;
        } catch (Exception e) {
            paymentStatus = PaymentStatus.FAILED;
            errorMessage = "An unexpected error occurred during payment finalization.";
            log.error("Unexpected error during payment finalization for ID {}: {}", paymentId, e.getMessage(), e);
            throw new PaymentProcessingException(errorMessage);
        }

        payment.setTransactionId(transactionId);
        payment.setPaymentStatus(paymentStatus);
        payment.setPaymentTimestamp(Instant.now());
        payment.setErrorMessage(errorMessage);

        Payment savedPayment = paymentRepository.save(payment);
        log.info("Payment record ID: {} status updated to {}.", savedPayment.getPaymentId(), savedPayment.getPaymentStatus());

        if (paymentStatus == PaymentStatus.SUCCESS) {

        	Optional<Booking> bookings = bookingRepository.findById(String.valueOf(payment.getBookingId()));
        	if(bookings.isPresent()) {
    			Booking booking = bookings.get();
    			booking.setStatus(BookingStatus.PAID);
    			bookingRepository.save(booking);
    		}
            log.info("Booking ID: {} status updated to COMPLETED.", payment.getBookingId());

        } else {
//            booking.setStatus(BookingStatus.CANCELLED);
//            bookingRepository.save(booking);
        	Optional<Booking> bookings = bookingRepository.findById(String.valueOf(payment.getBookingId()));
        	if(bookings.isPresent()) {
    			Booking booking = bookings.get();
    			booking.setStatus(BookingStatus.CANCELLED);
    			bookingRepository.save(booking);
    		}
            log.warn("Booking ID: {} status set to CANCELLED due to payment failure.", payment.getBookingId());
        }

        return mapToPaymentResponseDTO(savedPayment, paymentStatus == PaymentStatus.SUCCESS ? "Payment successful" : "Payment failed");
    }

    @Override
    @Transactional
    public PaymentResponseDTO finalizeUpiPayment(UpiFinalizationRequestDTO request,Long paymentId) {
        log.info("Attempting to finalize UPI payment ID: {}", paymentId);

        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found with ID: " + paymentId));

        if (payment.getPaymentStatus() != PaymentStatus.PENDING) {
            throw new BadRequestException("Payment with ID " + paymentId + " cannot be finalized. Current status: " + payment.getPaymentStatus());
        }

        // --- NEW LOGIC: SET AND VALIDATE PAYMENT METHOD AT FINALIZATION ---
        if (payment.getPaymentMethod() != null) {
            throw new BadRequestException("Payment with ID " + paymentId + " has already been finalized with method: " + payment.getPaymentMethod());
        }
        payment.setPaymentMethod(PaymentMethod.UPI);
        // -----------------------------------------------------------------

        
        String transactionId = generateUniqueTransactionId();
        PaymentStatus paymentStatus = PaymentStatus.SUCCESS;
        String errorMessage = null;

        try {
            log.info("Processing UPI payment for UPI ID: {}", request.getUpiId());
            if ("fail@upi".equals(request.getUpiId())) {
                 throw new PaymentProcessingException("Simulated UPI payment failure.");
            }
            //payment.setUpiId(request.getUpiDetails().getUpiId());

            log.info("Payment gateway processing successful for payment ID: {}", paymentId);
        } catch (PaymentProcessingException | BadRequestException e) {
            paymentStatus = PaymentStatus.FAILED;
            errorMessage = e.getMessage();
            log.error("Payment finalization failed for payment ID {}: {}", paymentId, e.getMessage());
            throw e;
        } catch (Exception e) {
            paymentStatus = PaymentStatus.FAILED;
            errorMessage = "An unexpected error occurred during payment finalization.";
            log.error("Unexpected error during payment finalization for ID {}: {}", paymentId, e.getMessage(), e);
            throw new PaymentProcessingException(errorMessage);
        }

        payment.setTransactionId(transactionId);
        payment.setPaymentStatus(paymentStatus);
        payment.setPaymentTimestamp(Instant.now());
        payment.setErrorMessage(errorMessage);

        Payment savedPayment = paymentRepository.save(payment);
        log.info("Payment record ID: {} status updated to {}.", savedPayment.getPaymentId(), savedPayment.getPaymentStatus());

        if (paymentStatus == PaymentStatus.SUCCESS) {
//            booking.setStatus(BookingStatus.COMPLETED);
//            bookingRepository.save(booking);
        	Optional<Booking> bookings = bookingRepository.findById(String.valueOf(payment.getBookingId()));
        	if(bookings.isPresent()) {
    			Booking booking = bookings.get();
    			booking.setStatus(BookingStatus.PAID);
    			bookingRepository.save(booking);
    		}
            log.info("Booking ID: {} status updated to COMPLETED.", payment.getBookingId());
            //invoiceService.generateInvoice(savedPayment.getBookingId(), savedPayment.getPaymentId());
            //log.info("Invoice generation initiated for booking ID: {} and payment ID: {}", savedPayment.getBookingId(), savedPayment.getPaymentId());
        } else {
//            booking.setStatus(BookingStatus.CANCELLED);
//            bookingRepository.save(booking);
        	Optional<Booking> bookings = bookingRepository.findById(String.valueOf(payment.getBookingId()));
        	if(bookings.isPresent()) {
    			Booking booking = bookings.get();
    			booking.setStatus(BookingStatus.CANCELLED);
    			bookingRepository.save(booking);
    		}
            log.warn("Booking ID: {} status set to CANCELLED due to payment failure.", payment.getBookingId());
        }

        return mapToPaymentResponseDTO(savedPayment, paymentStatus == PaymentStatus.SUCCESS ? "Payment successful" : "Payment failed");
    }

    @Override
    @Transactional
    public PaymentResponseDTO finalizeNetBankingPayment(NetBankingFinalizationRequestDTO request,Long paymentId) {
        log.info("Attempting to finalize Net Banking payment ID: {}", paymentId);

        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found with ID: " + paymentId));

        if (payment.getPaymentStatus() != PaymentStatus.PENDING) {
            throw new BadRequestException("Payment with ID " + paymentId + " cannot be finalized. Current status: " + payment.getPaymentStatus());
        }

        // --- NEW LOGIC: SET AND VALIDATE PAYMENT METHOD AT FINALIZATION ---
        if (payment.getPaymentMethod() != null) {
            throw new BadRequestException("Payment with ID " + paymentId + " has already been finalized with method: " + payment.getPaymentMethod());
        }
        payment.setPaymentMethod(PaymentMethod.NET_BANKING);
        // -----------------------------------------------------------------

        

        String transactionId = generateUniqueTransactionId();
        PaymentStatus paymentStatus = PaymentStatus.SUCCESS;
        String errorMessage = null;

        try {
            log.info("Processing Net Banking payment for bank: {}", request.getBankName());
            if ("FailedBank".equals(request.getBankName())) {
                 throw new PaymentProcessingException("Simulated Net Banking payment failure.");
            }
            payment.setBankName(request.getBankName());

            log.info("Payment gateway processing successful for payment ID: {}", paymentId);
        } catch (PaymentProcessingException | BadRequestException e) {
            paymentStatus = PaymentStatus.FAILED;
            errorMessage = e.getMessage();
            log.error("Payment finalization failed for payment ID {}: {}", paymentId, e.getMessage());
            throw e;
        } catch (Exception e) {
            paymentStatus = PaymentStatus.FAILED;
            errorMessage = "An unexpected error occurred during payment finalization.";
            log.error("Unexpected error during payment finalization for ID {}: {}", paymentId, e.getMessage(), e);
            throw new PaymentProcessingException(errorMessage);
        }

        payment.setTransactionId(transactionId);
        payment.setPaymentStatus(paymentStatus);
        payment.setPaymentTimestamp(Instant.now());
        payment.setErrorMessage(errorMessage);

        Payment savedPayment = paymentRepository.save(payment);
        log.info("Payment record ID: {} status updated to {}.", savedPayment.getPaymentId(), savedPayment.getPaymentId());

        if (paymentStatus == PaymentStatus.SUCCESS) {
//            booking.setStatus(BookingStatus.COMPLETED);
//            bookingRepository.save(booking);
        	Optional<Booking> bookings = bookingRepository.findById(String.valueOf(payment.getBookingId()));
        	if(bookings.isPresent()) {
    			Booking booking = bookings.get();
    			booking.setStatus(BookingStatus.PAID);
    			bookingRepository.save(booking);
    		}
            log.info("Booking ID: {} status updated to COMPLETED.", payment.getBookingId());
            //invoiceService.generateInvoice(savedPayment.getBookingId(), savedPayment.getPaymentId());
            //log.info("Invoice generation initiated for booking ID: {} and payment ID: {}", savedPayment.getBookingId(), savedPayment.getPaymentId());
        } else {
//            booking.setStatus(BookingStatus.CANCELLED);
//            bookingRepository.save(booking);
        	Optional<Booking> bookings = bookingRepository.findById(String.valueOf(payment.getBookingId()));
        	if(bookings.isPresent()) {
    			Booking booking = bookings.get();
    			booking.setStatus(BookingStatus.CANCELLED);
    			bookingRepository.save(booking);
    		}
            log.warn("Booking ID: {} status set to CANCELLED due to payment failure.", payment.getBookingId());
        }

        return mapToPaymentResponseDTO(savedPayment, paymentStatus == PaymentStatus.SUCCESS ? "Payment successful" : "Payment failed");
    }

    @Override
    @Transactional(readOnly = true)
    public PaymentResponseDTO getPaymentDetails(Long paymentId) {
        log.info("Fetching payment details for payment ID: {}", paymentId);
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found with ID: " + paymentId));
        return mapToPaymentResponseDTO(payment, "Payment details fetched successfully");
    }

    private String generateUniqueTransactionId() {
        return "TRN" + Instant.now().toEpochMilli() + (int)(Math.random() * 100000);
    }

    private String deriveCardType(String cardNumber) {
        if (cardNumber == null || cardNumber.isEmpty()) return "Unknown";
        if (cardNumber.startsWith("4")) return "Visa";
        if (cardNumber.startsWith("5")) return "Mastercard";
        if (cardNumber.startsWith("34") || cardNumber.startsWith("37")) return "Amex";
        return "Unknown";
    }

    private PaymentResponseDTO mapToPaymentResponseDTO(Payment payment, String message) {
        PaymentResponseDTO dto = new PaymentResponseDTO();
        dto.setPaymentId(payment.getPaymentId());
        dto.setBookingId(payment.getBookingId());
        dto.setUserId(payment.getUserId());
        dto.setAmount(payment.getAmount());
        return dto;
    }
    
    public List<Payment> getMyPayments(String userId){
    	return paymentRepository.findByUserId(userId);
    }
}