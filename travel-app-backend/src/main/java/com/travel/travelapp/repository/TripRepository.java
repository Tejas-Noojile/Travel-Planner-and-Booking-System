package com.travel.travelapp.repository;

import com.travel.travelapp.entity.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TripRepository extends JpaRepository<Trip, String> {
    List<Trip> findByUserId(String userId);
}
