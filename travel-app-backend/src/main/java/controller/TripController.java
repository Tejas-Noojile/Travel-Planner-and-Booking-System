package com.travel.travelapp.controller;

import com.travel.travelapp.dto.TripDTO;
import com.travel.travelapp.service.TripService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trips")
public class TripController {

    private final TripService tripService;

    public TripController(TripService tripService) {
        this.tripService = tripService;
    }

    @GetMapping
    public ResponseEntity<List<TripDTO>> getMyTrips() {
        return ResponseEntity.ok(tripService.getTripsForCurrentUser());
    }

    @GetMapping("/{tripId}")
    public ResponseEntity<TripDTO> getTripById(@PathVariable String tripId) {
        return tripService.getTripById(tripId)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new RuntimeException("Trip not found"));
    }

    @PostMapping
    public ResponseEntity<TripDTO> createTrip(@RequestBody TripDTO tripDTO) {
        return ResponseEntity.ok(tripService.createTrip(tripDTO));
    }

    @DeleteMapping("/{tripId}")
    public ResponseEntity<Void> deleteTrip(@PathVariable String tripId) {
        tripService.deleteTrip(tripId);
        return ResponseEntity.noContent().build();
    }
}