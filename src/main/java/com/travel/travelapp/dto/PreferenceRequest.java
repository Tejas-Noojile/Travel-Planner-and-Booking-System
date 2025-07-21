package com.travel.travelapp.dto;

import lombok.Data;

@Data
public class PreferenceRequest {
    private String travelInterests;
    private String budgetRange;
}
