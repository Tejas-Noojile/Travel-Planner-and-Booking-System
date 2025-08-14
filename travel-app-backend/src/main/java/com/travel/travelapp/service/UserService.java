package com.travel.travelapp.service;

import java.util.List;

import com.travel.travelapp.dto.PasswordChangeRequest;
import com.travel.travelapp.entity.User;

public interface UserService {
    User getCurrentUser();

    void updateProfile(User updated);

    void changePassword(PasswordChangeRequest request);

    void deactivateAccount();

    void deleteAccount();

    List<User> findAllUsers();

    void deleteUserById(String userId);
}
