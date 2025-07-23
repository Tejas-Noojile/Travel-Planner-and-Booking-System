package com.travel.travelapp.service;

import com.travel.travelapp.entity.Booking;
import com.travel.travelapp.repository.BookingRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookingService {

    private final BookingRepository repository;

    public BookingService(BookingRepository repository) {
        this.repository = repository;
    }

    public Booking createBooking(Booking booking) {
        return repository.save(booking);
    }

    public List<Booking> getBookingsByUser(String userId) {
        return repository.findByUserId(userId);
    }

    public List<Booking> getBookingsByTrip(String tripId) {
        return repository.findByTripId(tripId);
    }

    public Optional<Booking> getBookingById(String id) {
        return repository.findById(id);
    }

    public Booking updateBooking(Booking booking) {
        return repository.save(booking);
    }

    public void cancelBooking(String id) {
        repository.deleteById(id);
    }
}
