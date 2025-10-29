package com.travel.travelapp.service.impl;

import com.travel.travelapp.dto.PasswordChangeRequest;
import com.travel.travelapp.entity.User;
import com.travel.travelapp.exception.BadRequestException;
import com.travel.travelapp.exception.ResourceNotFoundException;
import com.travel.travelapp.repository.UserRepository;
import com.travel.travelapp.service.UserService;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String emailOrPhone = auth.getName();
        logger.debug("Getting current user by emailOrPhone={}", emailOrPhone);
        return userRepository.findByEmail(emailOrPhone)
                .orElseGet(() -> userRepository.findByPhone(emailOrPhone)
                        .orElseThrow(() -> {
                            logger.warn("User not found for emailOrPhone={}", emailOrPhone);
                            return new ResourceNotFoundException("User not found");
                        }));
    }

    @Override
    public void updateProfile(User updated) {
        logger.info("Updating profile for userId={}", updated.getUserId());
        User existingUser = getCurrentUser();
        existingUser.setName(updated.getName());
        existingUser.setEmail(updated.getEmail());
        existingUser.setPhone(updated.getPhone());
        userRepository.save(existingUser);
        logger.info("Profile updated for userId={}", existingUser.getUserId());
    }

    @Override
    public void changePassword(PasswordChangeRequest request) {
        logger.info("Changing password for current user");
        User user = getCurrentUser();
        if (!user.getPasswordHash().equals(request.getOldPassword())) {
            logger.warn("Old password does not match for userId={}", user.getUserId());
            throw new BadRequestException("Old password is incorrect");
        } else {
            user.setPasswordHash(request.getNewPassword());
            userRepository.save(user);
            logger.info("Password changed for userId={}", user.getUserId());
        }
    }

    @Override
    public void deactivateAccount() {
        logger.info("Deactivating account for current user");
        User user = getCurrentUser();
        user.setIsActive(false);
        userRepository.save(user);
        SecurityContextHolder.clearContext();
        logger.info("Account deactivated for userId={}", user.getUserId());
    }

    @Override
    public void deleteAccount() {
        logger.info("Deleting account for current user");
        User user = getCurrentUser();
        userRepository.delete(user);
        SecurityContextHolder.clearContext();
        logger.info("Account deleted for userId={}", user.getUserId());
    }
    @Override
    public List<User> findAllUsers() {
        logger.info("Fetching all users");
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) {
            logger.warn("No users found");
            throw new ResourceNotFoundException("No users found");
        }
        return users;
    }
    @Override
    public void deleteUserById(String userId) {
        logger.info("Deleting user with id={}", userId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> {
                    logger.warn("User not found for id={}", userId);
                    return new ResourceNotFoundException("User not found");
                });
        userRepository.delete(user);
        logger.info("User deleted with id={}", userId);

    }


}