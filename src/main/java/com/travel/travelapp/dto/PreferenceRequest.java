package com.travel.travelapp.dto;

import lombok.Data;

@Data
public class PreferenceRequest {
    private String travelInterests;

    public String getTravelInterests() {
        return travelInterests;
    }

    public void setTravelInterests(String travelInterests) {
        this.travelInterests = travelInterests;
    }

    public String getBudgetRange() {
        return budgetRange;
    }

    public void setBudgetRange(String budgetRange) {
        this.budgetRange = budgetRange;
    }

    private String budgetRange;
}
