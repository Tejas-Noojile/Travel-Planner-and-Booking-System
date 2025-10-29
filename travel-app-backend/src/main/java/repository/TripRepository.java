package com.travel.travelapp.repository;

import com.travel.travelapp.entity.Trip;
import com.travel.travelapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TripRepository extends JpaRepository<Trip, String> {
    List<Trip> findByUser(User user);
}