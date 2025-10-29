
package com.travel.travelapp.service;

import com.travel.travelapp.dto.PreferenceRequest;
import com.travel.travelapp.dto.PreferenceResponse;

public interface PreferenceService {
    void createPreference(PreferenceRequest request);
    PreferenceResponse getPreference();
    void updatePreference(PreferenceRequest request);
    void deletePreference();
}