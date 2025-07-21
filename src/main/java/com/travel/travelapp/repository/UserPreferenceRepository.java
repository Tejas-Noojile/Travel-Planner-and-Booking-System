package com.travel.travelapp.repository;

import com.travel.travelapp.entity.UserPreference;
import com.travel.travelapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserPreferenceRepository extends JpaRepository<UserPreference, UUID> {
    Optional<UserPreference> findByUser(User user);
}
