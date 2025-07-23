package com.travel.travelapp.service;

import com.travel.travelapp.dto.TripDTO;
import com.travel.travelapp.entity.Trip;
import com.travel.travelapp.repository.TripRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TripService {
    private final TripRepository tripRepository;

    public TripService(TripRepository tripRepository) {
        this.tripRepository = tripRepository;
    }

    public TripDTO createTrip(TripDTO dto) {
        Trip trip = toEntity(dto);
        Trip savedTrip = tripRepository.save(trip);
        return toDTO(savedTrip);
    }

    public TripDTO getTripById(String tripId) {
        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new RuntimeException("Trip not found with id: " + tripId));
        return toDTO(trip);
    }

    public List<TripDTO> getTripsByUser(String userId) {
        List<Trip> trips = tripRepository.findByUserId(userId);
        return trips.stream().map(this::toDTO).collect(Collectors.toList());
    }

    public TripDTO updateTrip(String id, TripDTO dto) {
        Optional<Trip> existingTripOpt = tripRepository.findById(id);
        if (existingTripOpt.isEmpty()) {
            throw new RuntimeException("Trip not found with id: " + id);
        }

        Trip existingTrip = existingTripOpt.get();

        // Update fields
        existingTrip.setTitle(dto.getTitle());
        existingTrip.setDestination(dto.getDestination());
        existingTrip.setStartDate(dto.getStartDate());
        existingTrip.setEndDate(dto.getEndDate());
        existingTrip.setNumPeople(dto.getNumPeople());
        existingTrip.setStatus(dto.getStatus());

        Trip updatedTrip = tripRepository.save(existingTrip);
        return toDTO(updatedTrip);
    }

    public void deleteTrip(String tripId) {
        tripRepository.deleteById(tripId);
    }

    // Helper: Entity → DTO
    private TripDTO toDTO(Trip trip) {
        TripDTO dto = new TripDTO();
        dto.setTripId(trip.getTripId());
        dto.setUserId(trip.getUserId());
        dto.setTitle(trip.getTitle());
        dto.setDestination(trip.getDestination());
        dto.setStartDate(trip.getStartDate());
        dto.setEndDate(trip.getEndDate());
        dto.setNumPeople(trip.getNumPeople());
        dto.setStatus(trip.getStatus());
        return dto;
    }

    // Helper: DTO → Entity
    private Trip toEntity(TripDTO dto) {
        Trip trip = new Trip();
        trip.setTripId(dto.getTripId());
        trip.setUserId(dto.getUserId());
        trip.setTitle(dto.getTitle());
        trip.setDestination(dto.getDestination());
        trip.setStartDate(dto.getStartDate());
        trip.setEndDate(dto.getEndDate());
        trip.setNumPeople(dto.getNumPeople());
        trip.setStatus(dto.getStatus());
        return trip;
    }
}
