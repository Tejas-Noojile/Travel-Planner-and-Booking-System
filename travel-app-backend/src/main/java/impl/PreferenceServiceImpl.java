package com.travel.travelapp.service.impl;

import com.travel.travelapp.dto.PreferenceRequest;
import com.travel.travelapp.dto.PreferenceResponse;
import com.travel.travelapp.entity.User;
import com.travel.travelapp.entity.UserPreference;
import com.travel.travelapp.repository.UserPreferenceRepository;
import com.travel.travelapp.service.PreferenceService;
import com.travel.travelapp.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PreferenceServiceImpl implements PreferenceService {

    private final UserPreferenceRepository userPreferenceRepository;
    private final UserService userService;

    public PreferenceServiceImpl(UserPreferenceRepository userPreferenceRepository, UserService userService) {
        this.userPreferenceRepository = userPreferenceRepository;
        this.userService = userService;
    }

    @Override
    @Transactional
    public void updatePreference(PreferenceRequest request) {
        User currentUser = userService.getCurrentUser();
        if (currentUser == null) {
            throw new RuntimeException("User not found or not authenticated.");
        }
        UserPreference userPreference = userPreferenceRepository.findByUser(currentUser)
                .orElse(new UserPreference());
        userPreference.setUser(currentUser);
        userPreference.setTravelInterests(request.getTravelInterests());
        userPreference.setBudgetRange(request.getBudgetRange());
        userPreferenceRepository.save(userPreference);
    }

    @Override
    public PreferenceResponse getPreference() {
        User currentUser = userService.getCurrentUser();
        UserPreference userPreference = userPreferenceRepository.findByUser(currentUser)
                .orElse(new UserPreference());

        PreferenceResponse response = new PreferenceResponse();
        response.setTravelInterests(userPreference.getTravelInterests());
        response.setBudgetRange(userPreference.getBudgetRange());
        return response;
    }

    @Override
    public void createPreference(PreferenceRequest request) {
        updatePreference(request);
    }

    @Override
    public void deletePreference() {
        User currentUser = userService.getCurrentUser();
        userPreferenceRepository.findByUser(currentUser).ifPresent(userPreferenceRepository::delete);
    }
}
