package com.travel.travelapp.service.impl;

import com.travel.travelapp.dto.BookingDTO;
import com.travel.travelapp.entity.Booking;
import com.travel.travelapp.repository.BookingRepository;
import com.travel.travelapp.service.BookingService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingServiceImpl implements BookingService {

    private final BookingRepository repo;

    public BookingServiceImpl(BookingRepository repo) {
        this.repo = repo;
    }

    private BookingDTO toDTO(Booking b) {
        return new BookingDTO(
            b.getBookingId(),
            b.getUserId(),
            b.getTripId(),
            b.getBookingType(),
            b.getVendorId(),
            b.getDetails(),
            b.getStartDatetime(),
            b.getEndDatetime(),
            b.getStatus()
        );
    }

    private Booking toEntity(BookingDTO d) {
        Booking b = new Booking();
        if (d.getBookingId() != null) b.setBookingId(d.getBookingId());
        b.setUserId(d.getUserId());
        b.setTripId(d.getTripId());
        b.setBookingType(d.getBookingType());
        b.setVendorId(d.getVendorId());
        b.setDetails(d.getDetails());
        b.setStartDatetime(d.getStartDatetime());
        b.setEndDatetime(d.getEndDatetime());
        b.setStatus(d.getStatus());
        return b;
    }

    @Override
    public BookingDTO createBooking(BookingDTO dto) {
        Booking saved = repo.save(toEntity(dto));
        return toDTO(saved);
    }

    @Override
    public BookingDTO getBookingById(String bookingId) {
        return repo.findById(bookingId)
                   .map(this::toDTO)
                   .orElseThrow(() -> new RuntimeException("Booking not found"));
    }

    @Override
    public List<BookingDTO> getBookingsByUserId(String userId) {
        return repo.findByUserId(userId).stream()
                   .map(this::toDTO)
                   .collect(Collectors.toList());
    }

    @Override
    public List<BookingDTO> getBookingsByTripId(String tripId) {
        return repo.findByTripId(tripId).stream()
                   .map(this::toDTO)
                   .collect(Collectors.toList());
    }

    @Override
    public BookingDTO updateBooking(String bookingId, BookingDTO dto) {
        getBookingById(bookingId);
        dto.setBookingId(bookingId);
        Booking updated = repo.save(toEntity(dto));
        return toDTO(updated);
    }

    @Override
    public void deleteBooking(String bookingId) {
        repo.deleteById(bookingId);
    }
}
