package com.travel.travelapp.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "bookings")
public class Booking {

    @Id
    @Column(name = "booking_id", columnDefinition = "CHAR(36)")
    private String bookingId = UUID.randomUUID().toString();

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
    @Column(name = "details")
    private String details;

    @Column(name = "start_datetime")
    private LocalDateTime startDatetime;

    @Column(name = "end_datetime")
    private LocalDateTime endDatetime;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private BookingStatus status = BookingStatus.CONFIRMED;

    public Booking() { }

    public Booking(String bookingId, String userId, String tripId,
                   BookingType bookingType, String vendorId, String details,
                   LocalDateTime startDatetime, LocalDateTime endDatetime,
                   BookingStatus status) {
        this.bookingId = bookingId;
        this.userId = userId;
        this.tripId = tripId;
        this.bookingType = bookingType;
        this.vendorId = vendorId;
        this.details = details;
        this.startDatetime = startDatetime;
        this.endDatetime = endDatetime;
        this.status = status;
    }

    // Getters & Setters
    public String getBookingId() { return bookingId; }
    public void setBookingId(String bookingId) { this.bookingId = bookingId; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getTripId() { return tripId; }
    public void setTripId(String tripId) { this.tripId = tripId; }

    public BookingType getBookingType() { return bookingType; }
    public void setBookingType(BookingType bookingType) { this.bookingType = bookingType; }

    public String getVendorId() { return vendorId; }
    public void setVendorId(String vendorId) { this.vendorId = vendorId; }

    public String getDetails() { return details; }
    public void setDetails(String details) { this.details = details; }

    public LocalDateTime getStartDatetime() { return startDatetime; }
    public void setStartDatetime(LocalDateTime startDatetime) { this.startDatetime = startDatetime; }

    public LocalDateTime getEndDatetime() { return endDatetime; }
    public void setEndDatetime(LocalDateTime endDatetime) { this.endDatetime = endDatetime; }

    public BookingStatus getStatus() { return status; }
    public void setStatus(BookingStatus status) { this.status = status; }
}
