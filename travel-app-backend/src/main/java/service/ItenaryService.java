package com.travel.travelapp.service;

import com.travel.travelapp.entity.Itenary;
import com.travel.travelapp.repository.ItenaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItenaryService {

    @Autowired
    private ItenaryRepository itineraryRepository;

    @Autowired
    private UserService userService;

    public Itenary saveItinerary(Itenary itinerary) {
        var currentUser = userService.getCurrentUser();
        System.out.println("Saving itinerary for user: " + currentUser.getUserId());
        return itineraryRepository.save(itinerary);
    }

    public List<Itenary> getItinerariesByTripId(String tripId) {
        return itineraryRepository.findByTripId(tripId);
    }

    public Optional<Itenary> getItineraryById(String id) {
        return itineraryRepository.findById(id);
    }

    public void deleteItinerary(String id) {
        itineraryRepository.deleteById(id);
    }
}