package com.travel.travelapp.repository;

import com.travel.travelapp.entity.Itenary;
// import com.travel.travelapp.entity.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ItenaryRepository extends JpaRepository<Itenary, String> {
    List<Itenary> findByTripId(String tripId);
}
