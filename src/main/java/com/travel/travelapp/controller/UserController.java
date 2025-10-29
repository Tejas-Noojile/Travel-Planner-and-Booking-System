package com.travel.travelapp.controller;

import com.travel.travelapp.dto.PasswordChangeRequest;
import com.travel.travelapp.entity.User;
import com.travel.travelapp.service.impl.UserServiceImpl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')") 
    public ResponseEntity<List<User>> getAllUsers() {
        logger.info("Admin request to fetch all users");
        return ResponseEntity.ok(userService.findAllUsers());
    }
    
    @DeleteMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN')") 
    public ResponseEntity<Void> deleteUser(@PathVariable String userId) {
        logger.info("Admin request to delete user with id={}", userId);
        userService.deleteUserById(userId);
        return ResponseEntity.ok().build();
    }
}