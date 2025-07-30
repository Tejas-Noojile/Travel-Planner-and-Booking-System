package com.travel.travelapp.controller;

import com.travel.travelapp.dto.PasswordChangeRequest;
import com.travel.travelapp.entity.User;
import com.travel.travelapp.service.impl.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserServiceImpl userService;

    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public User getProfile() {
        logger.info("Fetching current user profile");
        return userService.getCurrentUser();
    }

    @PutMapping("/me")
    public void updateProfile(@RequestBody User updated) {
        logger.info("Updating user profile for userId={}", updated.getUserId());
        userService.updateProfile(updated);
    }

    @PutMapping("/me/change-password")
    public void changePassword(@RequestBody PasswordChangeRequest request) {
        logger.info("Changing password for current user");
        userService.changePassword(request);
    }

    @DeleteMapping("/me")
    public void deactivateAccount() {
        logger.info("Deactivating current user account");
        userService.deactivateAccount();
    }

    @DeleteMapping("/me/delete")
    public void deleteAccount() {
        logger.info("Deleting current user account");
        userService.deleteAccount();
    }
}