package com.travel.travelapp.entity;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "invoices")
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "invoiceId", callSuper = false)
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long invoiceId;

    @Column( nullable = false, unique = true) 
    private String tripId; 

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(nullable = false) 
    private Long paymentId; 

    @Column(nullable = false, unique = true, length = 100)
    private String invoiceNumber;

    @Column(nullable = false)
    private Instant invoiceDate = Instant.now();

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal totalAmount;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal taxAmount;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal netAmount;

    @Column(nullable = false, name = "created_at", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(nullable = false, name = "updated_at")
    private Date updated_at = new Date();

    @Column(length = 255)
    private String invoicePdfUrl;
}
