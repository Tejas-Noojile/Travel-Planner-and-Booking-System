package com.travel.travelapp.repository;

import com.travel.travelapp.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface HotelRepository extends JpaRepository<Hotel, Long> {
    // Find all hotels in a specific city, ignoring case
    List<Hotel> findByCityIgnoreCase(String city);
}
