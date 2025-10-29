package com.travel.travelapp.service;

import com.travel.travelapp.dto.InvoiceResponseDTO;

public interface InvoiceService {
    InvoiceResponseDTO generateInvoice(String userId,String TripId, Long paymentId);
    InvoiceResponseDTO getInvoiceDetails(Long invoiceId);
    InvoiceResponseDTO getInvoiceByTripId(String TripId);
    byte[] downloadInvoiceAsPdf(String invoiceNumber);
}