package com.travel.travelapp.repository;

import com.travel.travelapp.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, String> {
    List<Booking> findByUserId(String userId);
    List<Booking> findByTripId(String tripId);
}
