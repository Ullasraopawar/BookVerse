package com.bookverse.bookverse_backend.service;

import com.bookverse.bookverse_backend.dto.ChangePasswordDTO;
import com.bookverse.bookverse_backend.dto.UpdateProfileDTO;
import com.bookverse.bookverse_backend.dto.UserProfileDTO;

public interface UserService {

    UserProfileDTO getProfile(String email);

    UserProfileDTO updateProfile(
            String email,
            UpdateProfileDTO request);

    void changePassword(
            String email,
            ChangePasswordDTO request);
}