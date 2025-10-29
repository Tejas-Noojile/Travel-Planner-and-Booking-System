package com.travel.travelapp.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "bookings")
@Data
public class Booking {

    @Id
    @Column(name = "booking_id", columnDefinition = "CHAR(36)")
    private String bookingId;

    @Column(name = "user_id", columnDefinition = "CHAR(36)", nullable = false)
    private String userId;

    @Column(name = "trip_id", columnDefinition = "CHAR(36)", nullable = false)
    private String tripId;

    @Enumerated(EnumType.STRING)
    @Column(name = "booking_type", nullable = false)
    private BookingType bookingType;

    @Column(name = "vendor_id", columnDefinition = "CHAR(36)")
    private String vendorId;

    @Lob
    @Column(name = "details", columnDefinition = "TEXT")
    private String details;

    private LocalDateTime startDatetime;
    private LocalDateTime endDatetime;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private BookingStatus status = BookingStatus.CONFIRMED;

    private double amount;

    @PrePersist
    public void prePersist() {
        if (this.bookingId == null) {
            this.bookingId = UUID.randomUUID().toString();
        }
    }
}
