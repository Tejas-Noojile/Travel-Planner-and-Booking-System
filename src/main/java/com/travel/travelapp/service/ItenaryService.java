package com.travel.travelapp.service;

import com.travel.travelapp.entity.Itenary;
import com.travel.travelapp.repository.ItenaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ItenaryService {

    @Autowired
    private ItenaryRepository itineraryRepository;

    public Itenary saveItinerary(Itenary itinerary) {
        return itineraryRepository.save(itinerary);
    }

    public List<Itenary> getItinerariesByTripId(UUID tripId) {
        return itineraryRepository.findByTripId(tripId);
    }

    public Itenary getItineraryById(UUID id) {
        return itineraryRepository.findById(id).orElse(null);
    }

    public void deleteItinerary(UUID id) {
        itineraryRepository.deleteById(id);
    }
}