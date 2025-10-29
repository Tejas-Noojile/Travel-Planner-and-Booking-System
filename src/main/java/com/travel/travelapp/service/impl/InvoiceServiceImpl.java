package com.travel.travelapp.service.impl;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.travel.travelapp.dto.InvoiceResponseDTO;
import com.travel.travelapp.exception.BadRequestException;
import com.travel.travelapp.exception.ResourceNotFoundException;
import com.travel.travelapp.entity.Invoice;
import com.travel.travelapp.entity.Payment;
import com.travel.travelapp.repository.InvoiceRepository;
import com.travel.travelapp.repository.PaymentRepository;
import com.travel.travelapp.service.InvoiceService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepository;

    private final PaymentRepository paymentRepository;

    @Override
    @Transactional
    public InvoiceResponseDTO generateInvoice(String userId, String tripId, Long paymentId) {
        log.info("Attempting to generate invoice for trip ID: {} and payment ID: {}", tripId, paymentId);
        Optional<Invoice> invoices = invoiceRepository.findByTripId(tripId);

        if (invoices.isPresent()) {
            return mapToInvoiceResponseDTO(invoices.get(), "Invoice generated successfully");
        }

        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found with ID: " + paymentId));

        if (!tripId.equals(payment.getTripId())) {
            throw new BadRequestException("Trip ID and Payment's Trip ID do not match.");
        }

        BigDecimal totalAmount = new BigDecimal(payment.getAmount());

        BigDecimal taxRate = new BigDecimal("0.12");

        BigDecimal divisor = BigDecimal.ONE.add(taxRate);

        BigDecimal netAmount = totalAmount.divide(divisor, 2, RoundingMode.HALF_UP);

        BigDecimal taxAmount = totalAmount.subtract(netAmount);

        Invoice invoice = new Invoice();
        invoice.setTripId(tripId);
        invoice.setPaymentId(paymentId);
        invoice.setUserId(userId);
        invoice.setInvoiceNumber("INV-" + Instant.now().toEpochMilli());
        invoice.setInvoiceDate(Instant.now());
        invoice.setTotalAmount(totalAmount);
        invoice.setTaxAmount(taxAmount);
        invoice.setNetAmount(netAmount);
        invoice.setInvoicePdfUrl(
                "http://localhost:8080/api/v1/invoices/user/" + invoice.getInvoiceNumber() + "/download");

        Invoice savedInvoice = invoiceRepository.save(invoice);
        log.info("Invoice generated successfully with ID: {} for Trip ID: {}", savedInvoice.getInvoiceId(), tripId);

        return mapToInvoiceResponseDTO(savedInvoice, "Invoice generated successfully");
    }

    @Override
    @Transactional(readOnly = true)
    public InvoiceResponseDTO getInvoiceDetails(Long invoiceId) {
        log.info("Fetching invoice details for invoice ID: {}", invoiceId);
        Invoice invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new ResourceNotFoundException("Invoice not found with ID: " + invoiceId));
        return mapToInvoiceResponseDTO(invoice, "Invoice details fetched successfully");
    }

    @Override
    @Transactional(readOnly = true)
    public InvoiceResponseDTO getInvoiceByTripId(String tripId) {
        log.info("Fetching invoice details for Trip ID: {}", tripId);
        Invoice invoice = invoiceRepository.findByTripId(tripId)
                .orElseThrow(() -> new ResourceNotFoundException("Invoice not found for Trip ID: " + tripId));
        return mapToInvoiceResponseDTO(invoice, "Invoice details fetched successfully by trip ID");
    }

    @Override
    @Transactional(readOnly = true)
    public byte[] downloadInvoiceAsPdf(String invoiceNumber) {
        log.info("Attempting to generate PDF for invoice ID: {}", invoiceNumber);
        Invoice invoice = invoiceRepository.findByInvoiceNumber(invoiceNumber);

        if (invoice == null) {
            throw new ResourceNotFoundException("Invoice not found with ID: " + invoiceNumber);
        }
        Long invoiceId = invoice.getInvoiceId();
        Document document = new Document();
        ByteArrayOutputStream baos = new ByteArrayOutputStream(); // creates an in-memory stream for writing bytes. You
                                                                  // can later retrieve the data as a byte array.

        try {
            PdfWriter.getInstance(document, baos);// Write everything from this document into the memory buffer (baos)
            document.open();

            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 24, BaseColor.BLACK); // A utility method
                                                                                                   // to create fonts
                                                                                                   // easily.
            Font headingFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14, BaseColor.DARK_GRAY);
            Font normalFont = FontFactory.getFont(FontFactory.HELVETICA, 12, BaseColor.BLACK);

            // Title
            Paragraph title = new Paragraph("Travel Booking Invoice", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            Paragraph separator = new Paragraph(new Chunk("------------------------------------", normalFont));
            separator.setAlignment(Element.ALIGN_CENTER);
            document.add(separator);

            // Invoice Details
            document.add(new Paragraph("Invoice Number: " + invoice.getInvoiceNumber(), normalFont));
            document.add(
                    new Paragraph("Invoice Date: " + invoice.getInvoiceDate().atZone(java.time.ZoneId.systemDefault())
                            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), normalFont));
            document.add(new Paragraph("Trip ID: " + invoice.getTripId(), normalFont));
            document.add(new Paragraph("User ID: " + invoice.getUserId(), normalFont));
            document.add(new Paragraph("Payment ID: " + invoice.getPaymentId(), normalFont));

            document.add(new Paragraph(Chunk.NEWLINE)); // Spacer

            // Amount Breakdown
            document.add(new Paragraph("Amount Breakdown:", headingFont));
            document.add(new Paragraph("Net Amount: INR " + String.format("%.2f", invoice.getNetAmount()), normalFont));
            document.add(new Paragraph("Tax Amount (12%): INR " + String.format("%.2f", invoice.getTaxAmount()),
                    normalFont));
            Paragraph totalAmount = new Paragraph(
                    "Total Amount: INR " + String.format("%.2f", invoice.getTotalAmount()), headingFont);
            totalAmount.setAlignment(Element.ALIGN_RIGHT);
            document.add(totalAmount);

            document.add(new Paragraph(Chunk.NEWLINE)); // Spacer

            // Footer
            Paragraph footer = new Paragraph("Thank you for your business!", normalFont);
            footer.setAlignment(Element.ALIGN_CENTER);
            document.add(footer);

            document.close();
            log.info("PDF content generated for invoice ID: {}", invoiceId);
            return baos.toByteArray();

        } catch (DocumentException e) {
            log.error("Document creation error for invoice ID {}: {}", invoiceId, e.getMessage(), e);
            throw new RuntimeException("Failed to create invoice PDF document for ID: " + invoiceId, e);
        } catch (Exception e) {
            log.error("Unexpected error generating PDF for invoice ID {}: {}", invoiceId, e.getMessage(), e);
            throw new RuntimeException("Failed to generate invoice PDF for ID: " + invoiceId, e);
        } finally {
            if (document.isOpen()) {
                document.close();
            }
        }
    }

    private InvoiceResponseDTO mapToInvoiceResponseDTO(Invoice invoice, String message) {
        InvoiceResponseDTO dto = new InvoiceResponseDTO();
        dto.setInvoiceId(invoice.getInvoiceId());
        dto.setInvoiceNumber(invoice.getInvoiceNumber());
        dto.setTripId(invoice.getTripId());
        dto.setUserId(invoice.getUserId());
        dto.setPaymentId(invoice.getPaymentId());
        dto.setInvoiceDate(invoice.getInvoiceDate());
        dto.setTotalAmount(invoice.getTotalAmount());
        dto.setTaxAmount(invoice.getTaxAmount());
        dto.setNetAmount(invoice.getNetAmount());
        dto.setInvoicePdfUrl(invoice.getInvoicePdfUrl());
        dto.setMessage(message);
        return dto;
    }
}