package com.travel.travelapp.service;

import com.travel.travelapp.dto.InvoiceResponseDTO;

public interface InvoiceService {
    InvoiceResponseDTO generateInvoice(String userId,String bookingId, Long paymentId);
    InvoiceResponseDTO getInvoiceDetails(Long invoiceId);
    InvoiceResponseDTO getInvoiceByBookingId(String bookingId);
    byte[] downloadInvoiceAsPdf(String invoiceNumber);
}