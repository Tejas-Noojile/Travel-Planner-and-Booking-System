package com.travel.travelapp.dto;

import com.travel.travelapp.entity.BookingStatus;
import com.travel.travelapp.entity.BookingType;
import java.time.LocalDateTime;

public class BookingDTO {
    private String bookingId;
    private String userId;
    private String tripId;
    private BookingType bookingType;
    private String vendorId;
    private String details;
    private LocalDateTime startDatetime;
    private LocalDateTime endDatetime;
    private BookingStatus status;

    public BookingDTO() { }

    public BookingDTO(String bookingId, String userId, String tripId,
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
