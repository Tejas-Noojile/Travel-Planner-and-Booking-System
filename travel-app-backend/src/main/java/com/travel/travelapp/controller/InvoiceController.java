package com.travel.travelapp.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.travel.travelapp.dto.InvoiceResponseDTO; 
import com.travel.travelapp.exception.ResourceNotFoundException;
import com.travel.travelapp.entity.Payment;
import com.travel.travelapp.repository.PaymentRepository;
import com.travel.travelapp.service.InvoiceService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/invoices") 
@RequiredArgsConstructor 
@Slf4j 
public class InvoiceController {

    private final InvoiceService invoiceService;
    private final PaymentRepository paymentRepository;


    @PostMapping("user/generate") 
    public ResponseEntity<InvoiceResponseDTO> generateInvoice(@RequestParam Long paymentId) {
    	Payment payment = paymentRepository.findByPaymentId(paymentId).orElseThrow(() -> new ResourceNotFoundException("booking not found with ID: " + paymentId));
        log.info("Received request to generate invoice for booking ID: {} and payment ID: {}", payment.getBookingId(), paymentId);
        
        InvoiceResponseDTO responseDTO = invoiceService.generateInvoice(payment.getUserId(),payment.getBookingId(), paymentId);
        
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED); 
    }


    @GetMapping("user/{invoiceId}") 
    public ResponseEntity<InvoiceResponseDTO> getInvoiceDetails(@PathVariable Long invoiceId) {
        log.info("Received request to get invoice details for ID: {}", invoiceId);
        InvoiceResponseDTO responseDTO = invoiceService.getInvoiceDetails(invoiceId);
        return ResponseEntity.ok(responseDTO); 
    }


    @GetMapping("user/booking/{bookingId}") 
    public ResponseEntity<InvoiceResponseDTO> getInvoiceByBookingId(@PathVariable String bookingId) {
        log.info("Received request to get invoice details for booking ID: {}", bookingId);
        InvoiceResponseDTO responseDTO = invoiceService.getInvoiceByBookingId(bookingId);
        return ResponseEntity.ok(responseDTO); 
    }


    @GetMapping("user/{invoiceNumber}/download") 
    public ResponseEntity<byte[]> downloadInvoiceAsPdf(@PathVariable String invoiceNumber) {
    	
        log.info("Received request to download PDF for invoice ID: {}", invoiceNumber);
        byte[] pdfContent = invoiceService.downloadInvoiceAsPdf(invoiceNumber);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF); 
        String filename = "invoice-" + invoiceNumber + ".pdf";
        headers.setContentDispositionFormData("attachment", filename); 

        return new ResponseEntity<>(pdfContent, headers, HttpStatus.OK); 
    }
}