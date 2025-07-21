package com.travel.travelapp.service;

import com.travel.travelapp.dto.PasswordChangeRequest;
import com.travel.travelapp.entity.User;

public interface UserService {
    User getCurrentUser();
    void updateProfile(User updated);
    void changePassword(PasswordChangeRequest request);
    void deactivateAccount();
}
