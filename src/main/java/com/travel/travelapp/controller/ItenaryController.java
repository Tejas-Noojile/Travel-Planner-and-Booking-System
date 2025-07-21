package com.travel.travelapp.controller;

import com.travel.travelapp.entity.Itenary;
import com.travel.travelapp.service.ItenaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/itineraries")
public class ItenaryController {

    @Autowired
    private ItenaryService itineraryService;

    @PostMapping
    public Itenary createItinerary(@RequestBody Itenary itinerary) {
        return itineraryService.saveItinerary(itinerary);
    }

    @GetMapping("/trip/{tripId}")
    public List<Itenary> getItinerariesByTrip(@PathVariable String tripId) {
        return itineraryService.getItinerariesByTripId(UUID.fromString(tripId));
    }

    @GetMapping("/{id}")
    public Itenary getItineraryById(@PathVariable String id) {
        return itineraryService.getItineraryById(UUID.fromString(id));
    }

    @PutMapping("/{id}")
    public Itenary updateItinerary(@PathVariable String id, @RequestBody Itenary itinerary) {
        itinerary.setId(UUID.fromString(id));
        return itineraryService.saveItinerary(itinerary);
    }

    @DeleteMapping("/{id}")
    public void deleteItinerary(@PathVariable String id) {
        itineraryService.deleteItinerary(UUID.fromString(id));
    }
}