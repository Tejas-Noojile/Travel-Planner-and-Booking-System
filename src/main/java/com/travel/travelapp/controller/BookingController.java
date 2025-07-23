package com.travel.travelapp.controller;

import com.travel.travelapp.entity.Booking;
import com.travel.travelapp.service.BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService service;

    public BookingController(BookingService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Booking> create(@RequestBody Booking booking) {
        return ResponseEntity.ok(service.createBooking(booking));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Booking>> getByUser(@PathVariable String userId) {
        return ResponseEntity.ok(service.getBookingsByUser(userId));
    }

    @GetMapping("/trip/{tripId}")
    public ResponseEntity<List<Booking>> getByTrip(@PathVariable String tripId) {
        return ResponseEntity.ok(service.getBookingsByTrip(tripId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Booking> getById(@PathVariable String id) {
        return service.getBookingById(id)
                      .map(ResponseEntity::ok)
                      .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Booking> update(@PathVariable String id, @RequestBody Booking booking) {
        booking.setBookingId(id);
        return ResponseEntity.ok(service.updateBooking(booking));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancel(@PathVariable String id) {
        service.cancelBooking(id);
        return ResponseEntity.noContent().build();
    }
}
