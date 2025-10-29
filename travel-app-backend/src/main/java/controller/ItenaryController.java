package com.travel.travelapp.controller;

import com.travel.travelapp.exception.ResourceNotFoundException;
import com.travel.travelapp.entity.Itenary;
import com.travel.travelapp.service.ItenaryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/api/itineraries")
@CrossOrigin(origins = "http://localhost:4200")
public class ItenaryController {

    @Autowired
    private ItenaryService itenaryService;

    @PostMapping
    public ResponseEntity<Itenary> createItinerary(@RequestBody @Valid Itenary itenary) {
        return ResponseEntity.ok(itenaryService.saveItinerary(itenary));
    }

    @GetMapping("/trip")
    public ResponseEntity<List<Itenary>> getItinerariesByTrip(@RequestParam String tripId) {
        List<Itenary> itineraries = itenaryService.getItinerariesByTripId(tripId);
        return ResponseEntity.ok(itineraries);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Itenary> getItineraryById(@PathVariable String id) {
        return itenaryService.getItineraryById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Itenary not found with id: " + id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Itenary> updateItinerary(@PathVariable String id, @RequestBody Itenary itenary) {
        return itenaryService.getItineraryById(id)
                .map(existing -> {
                    itenary.setItemId(id);
                    return ResponseEntity.ok(itenaryService.saveItinerary(itenary));
                })
                .orElseThrow(() -> new ResourceNotFoundException("Itenary not found with id: " + id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItinerary(@PathVariable String id) {
        if (itenaryService.getItineraryById(id).isPresent()) {
            itenaryService.deleteItinerary(id);
            return ResponseEntity.ok().build();
        }
        throw new ResourceNotFoundException("Itenary not found with id: " + id);
    }
}