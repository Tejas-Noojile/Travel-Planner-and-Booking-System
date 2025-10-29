package com.travel.travelapp.service;

import com.travel.travelapp.dto.TripDTO;
import com.travel.travelapp.entity.Trip;
import com.travel.travelapp.entity.TripStatus;
import com.travel.travelapp.entity.User;
import com.travel.travelapp.repository.TripRepository;
import com.travel.travelapp.service.impl.UserServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class TripService {

    private final TripRepository tripRepository;
    private final UserServiceImpl userService;

    public TripService(TripRepository tripRepository, UserServiceImpl userService) {
        this.tripRepository = tripRepository;
        this.userService = userService;
    }

    public List<TripDTO> getTripsForCurrentUser() {
        User currentUser = userService.getCurrentUser();
        return tripRepository.findByUser(currentUser).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<TripDTO> getTripById(String tripId) {
        User currentUser = userService.getCurrentUser();
        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new RuntimeException("Trip not found"));
        if (!trip.getUser().getUserId().equals(currentUser.getUserId())) {
            throw new SecurityException("You are not authorized to view this trip");
        }
        return Optional.ofNullable(convertToDTO(trip));
    }
    public TripDTO createTrip(TripDTO tripDTO) {
        User currentUser = userService.getCurrentUser();
        Trip trip = convertToEntity(tripDTO);
        trip.setUser(currentUser);
        Trip savedTrip = tripRepository.save(trip);
        return convertToDTO(savedTrip);
    }

    public void deleteTrip(String tripId) {
        User currentUser = userService.getCurrentUser();
        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new RuntimeException("Trip not found"));
        if (!trip.getUser().getUserId().equals(currentUser.getUserId())) {
            throw new SecurityException("You are not authorized to delete this trip");
        }
        tripRepository.deleteById(tripId);
    }

    // Helper methods to convert between Entity and DTO
    private TripDTO convertToDTO(Trip trip) {
        TripDTO dto = new TripDTO();
        dto.setTripId(trip.getTripId());
        dto.setTitle(trip.getTitle());
        dto.setDestination(trip.getDestination());
        dto.setStartDate(trip.getStartDate());
        dto.setEndDate(trip.getEndDate());
        dto.setNumPeople(trip.getNumPeople());

        // Assuming TripStatus is an enum, you can convert it to a string
        if (trip.getStatus() != null) {
            dto.setStatus(trip.getStatus().name());
        } else {
            dto.setStatus("UNKNOWN");
        }
        return dto;
    }

    private Trip convertToEntity(TripDTO dto) {
        Trip trip = new Trip();
        trip.setTitle(dto.getTitle());
        trip.setDestination(dto.getDestination());
        trip.setStartDate(dto.getStartDate());
        trip.setEndDate(dto.getEndDate());
        trip.setNumPeople(dto.getNumPeople());//Convert status from String to TripStatus enum:
        if (dto.getStatus() != null) {
            try {
                trip.setStatus(TripStatus.valueOf(dto.getStatus().toUpperCase()));
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Invalid trip status: " + dto.getStatus());
            }
        } else {
            trip.setStatus(TripStatus.UNKNOWN); // Default status if not provided
        }

        return trip;
    }
}