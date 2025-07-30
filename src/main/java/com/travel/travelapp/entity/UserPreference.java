package com.travel.travelapp.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Table(name = "userpreferences")
@Data
public class UserPreference {
    @Id
    private UUID preferenceId;

    public UUID getPreferenceId() {
        return preferenceId;
    }

    public void setPreferenceId(UUID preferenceId) {
        this.preferenceId = preferenceId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

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

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String travelInterests;
    private String budgetRange;
}
