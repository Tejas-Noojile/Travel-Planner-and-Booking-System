package com.travel.travelapp.repository;

import com.travel.travelapp.entity.Itenary;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface ItenaryRepository extends JpaRepository<Itenary, UUID> {
    List<Itenary> findByTripId(UUID tripId);
}