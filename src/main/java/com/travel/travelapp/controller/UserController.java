package com.travel.travelapp.controller;

import com.travel.travelapp.dto.PasswordChangeRequest;
import com.travel.travelapp.entity.User;
import com.travel.travelapp.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public User getProfile() {
        return null;
    }

    @PutMapping("/me")
    public void updateProfile(@RequestBody User updated) {
    }

    @PutMapping("/me/change-password")
    public void changePassword(@RequestBody PasswordChangeRequest request) {
    }

    @DeleteMapping("/me")
    public void deactivateAccount() {
    }
}
