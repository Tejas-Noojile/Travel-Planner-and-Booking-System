package com.travel.travelapp.controller;

import com.travel.travelapp.dto.TripDTO;
import com.travel.travelapp.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trips")
public class TripController {

    @Autowired
    private TripService tripService;

    @PostMapping
    public ResponseEntity<TripDTO> createTrip(@RequestBody TripDTO dto) {
        return ResponseEntity.ok(tripService.createTrip(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TripDTO> getTrip(@PathVariable("id") String id) {
        return ResponseEntity.ok(tripService.getTripById(id));
    }

    @GetMapping
    public ResponseEntity<List<TripDTO>> getTripsByUser(@RequestParam("userId") String userId) {
        return ResponseEntity.ok(tripService.getTripsByUser(userId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TripDTO> updateTrip(@PathVariable("id") String id, @RequestBody TripDTO dto) {
        return ResponseEntity.ok(tripService.updateTrip(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTrip(@PathVariable("id") String id) {
        tripService.deleteTrip(id);
        return ResponseEntity.noContent().build();
    }
}
