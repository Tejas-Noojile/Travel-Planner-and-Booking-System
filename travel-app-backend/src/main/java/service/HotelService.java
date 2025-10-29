package com.travel.travelapp.service;

import com.travel.travelapp.entity.Hotel;
import com.travel.travelapp.repository.HotelRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HotelService {

    private final HotelRepository hotelRepository;

    public HotelService(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    public List<Hotel> findHotelsByCity(String city) {
        return hotelRepository.findByCityIgnoreCase(city);
    }

    public List<String> getAvailableCities() {
        return hotelRepository.findAll().stream()
                .map(Hotel::getCity)
                .distinct()
                .sorted()
                .collect(Collectors.toList());
    }
}