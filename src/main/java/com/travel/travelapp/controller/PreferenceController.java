package com.travel.travelapp.controller;

import com.travel.travelapp.dto.PreferenceRequest;
import com.travel.travelapp.service.PreferenceService;
import com.travel.travelapp.service.impl.PreferenceServiceImpl;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/preferences")
public class PreferenceController {

    private final PreferenceServiceImpl preferenceService;

    public PreferenceController(PreferenceServiceImpl preferenceService) {
        this.preferenceService = preferenceService;
    }

    @PostMapping
    public void createPreference(@RequestBody PreferenceRequest request) {
    }

    @GetMapping
    public PreferenceRequest getPreference() {
        return null;
    }

    @PutMapping
    public void updatePreference(@RequestBody PreferenceRequest request) {
    }

    @DeleteMapping
    public void deletePreference() {
    }
}
