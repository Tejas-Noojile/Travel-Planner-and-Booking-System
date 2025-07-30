package com.travel.travelapp.service.impl;

import com.travel.travelapp.dto.PreferenceRequest;
import com.travel.travelapp.service.PreferenceService;
import org.springframework.stereotype.Service;

@Service
public class PreferenceServiceImpl implements PreferenceService {
    private PreferenceRequest preference;

    @Override
    public void createPreference(PreferenceRequest request) {
        this.preference = request;
    }

    @Override
    public PreferenceRequest getPreference() {
        return this.preference;
    }

    @Override
    public void updatePreference(PreferenceRequest request) {
        this.preference = request;
    }

    @Override
    public void deletePreference() {
        this.preference = null;
    }
}