package com.bookverse.bookverse_backend.controller;

import com.bookverse.bookverse_backend.dto.ChangePasswordDTO;
import com.bookverse.bookverse_backend.dto.UpdateProfileDTO;
import com.bookverse.bookverse_backend.dto.UserProfileDTO;
import com.bookverse.bookverse_backend.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public UserProfileDTO getProfile(
            Authentication authentication) {

        return userService.getProfile(
                authentication.getName());
    }

    @PutMapping("/me")
    public UserProfileDTO updateProfile(
            @RequestBody UpdateProfileDTO request,
            Authentication authentication) {

        return userService.updateProfile(
                authentication.getName(),
                request);
    }

    @PutMapping("/change-password")
    public String changePassword(
            @RequestBody ChangePasswordDTO request,
            Authentication authentication) {

        userService.changePassword(
                authentication.getName(),
                request);

        return "Password updated successfully.";
    }
}