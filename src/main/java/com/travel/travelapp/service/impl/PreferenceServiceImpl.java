package com.travel.travelapp.service.impl;

import com.travel.travelapp.dto.PreferenceRequest;
import com.travel.travelapp.dto.PreferenceResponse;
import com.travel.travelapp.entity.User;
import com.travel.travelapp.entity.UserPreference;
import com.travel.travelapp.repository.UserPreferenceRepository;
import com.travel.travelapp.service.PreferenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class PreferenceServiceImpl implements PreferenceService {

    @Autowired
    private UserPreferenceRepository userPreferenceRepository;

    @Autowired
    private UserServiceImpl userService; // Inject UserServiceImpl

    @Override
    public void createPreference(PreferenceRequest request) {
        User user = userService.getCurrentUser();
        UserPreference preference = new UserPreference();
        preference.setPreferenceId(UUID.randomUUID());
        preference.setUser(user);
        preference.setTravelInterests(request.getTravelInterests());
        preference.setBudgetRange(request.getBudgetRange());
        userPreferenceRepository.save(preference);
    }

    @Override
    public PreferenceResponse getPreference() {
        User user = userService.getCurrentUser();
        Optional<UserPreference> prefOpt = userPreferenceRepository.findByUser(user);
        if (prefOpt.isPresent()) {
            UserPreference pref = prefOpt.get();
            PreferenceResponse response = new PreferenceResponse();
            response.setTravelInterests(pref.getTravelInterests());
            response.setBudgetRange(pref.getBudgetRange());
            return response;
        }
        return null;
    }

    @Override
    public void updatePreference(PreferenceRequest request) {
        User user = userService.getCurrentUser();
        Optional<UserPreference> prefOpt = userPreferenceRepository.findByUser(user);
        if (prefOpt.isPresent()) {
            UserPreference pref = prefOpt.get();
            pref.setTravelInterests(request.getTravelInterests());
            pref.setBudgetRange(request.getBudgetRange());
            userPreferenceRepository.save(pref);
        }
    }

    @Override
    public void deletePreference() {
        User user = userService.getCurrentUser();
        Optional<UserPreference> prefOpt = userPreferenceRepository.findByUser(user);
        prefOpt.ifPresent(userPreferenceRepository::delete);
    }
}