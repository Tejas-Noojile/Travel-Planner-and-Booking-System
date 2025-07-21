package com.travel.travelapp.service;

import com.travel.travelapp.dto.PreferenceRequest;

public interface PreferenceService {
    void createPreference(PreferenceRequest request);
    PreferenceRequest getPreference();
    void updatePreference(PreferenceRequest request);
    void deletePreference();
}
