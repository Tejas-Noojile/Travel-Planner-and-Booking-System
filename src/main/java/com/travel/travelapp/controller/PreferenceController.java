// src/main/java/com/travel/travelapp/controller/PreferenceController.java
package com.travel.travelapp.controller;

import com.travel.travelapp.dto.PreferenceRequest;
import com.travel.travelapp.dto.PreferenceResponse;
import com.travel.travelapp.service.PreferenceService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/preferences")
public class PreferenceController {

    private final PreferenceService preferenceService;

    public PreferenceController(PreferenceService preferenceService) {
        this.preferenceService = preferenceService;
    }

    @PostMapping
    public void createPreference(@RequestBody PreferenceRequest request) {
        preferenceService.createPreference(request);
    }

    @GetMapping
    public PreferenceResponse getPreference() {
        return preferenceService.getPreference();
    }

    @PutMapping
    public void updatePreference(@RequestBody PreferenceRequest request) {
        preferenceService.updatePreference(request);
    }

    @DeleteMapping
    public void deletePreference() {
        preferenceService.deletePreference();
    }
}