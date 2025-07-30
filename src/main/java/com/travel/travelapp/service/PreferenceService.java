package com.travel.travelapp.service;

import com.travel.travelapp.dto.PreferenceRequest;
import org.springframework.stereotype.Service;

public interface PreferenceService {
    void createPreference(PreferenceRequest request);
    PreferenceRequest getPreference();
    void updatePreference(PreferenceRequest request);
    void deletePreference();
}
