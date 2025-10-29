package com.travel.travelapp.controller;

import com.travel.travelapp.dto.BookingDTO;
import com.travel.travelapp.service.BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService svc;

    public BookingController(BookingService svc) {
        this.svc = svc;
    }

    @PostMapping
    public ResponseEntity<BookingDTO> create(@RequestBody BookingDTO dto) {
        return ResponseEntity.ok(svc.createBooking(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingDTO> getById(@PathVariable String id) {
        return ResponseEntity.ok(svc.getBookingById(id));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<BookingDTO>> getByUser(@PathVariable String userId) {
        return ResponseEntity.ok(svc.getBookingsByUserId(userId));
    }

    @GetMapping("/trip/{tripId}")
    public ResponseEntity<List<BookingDTO>> getByTrip(@PathVariable String tripId) {
        return ResponseEntity.ok(svc.getBookingsByTripId(tripId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookingDTO> update(@PathVariable String id,
            @RequestBody BookingDTO dto) {
        return ResponseEntity.ok(svc.updateBooking(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        svc.deleteBooking(id);
        return ResponseEntity.noContent().build();
    }
}

