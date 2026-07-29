package com.bookverse.bookverse_backend.service.impl;

import com.bookverse.bookverse_backend.dto.ChangePasswordDTO;
import com.bookverse.bookverse_backend.dto.UpdateProfileDTO;
import com.bookverse.bookverse_backend.dto.UserProfileDTO;
import com.bookverse.bookverse_backend.entity.User;
import com.bookverse.bookverse_backend.exception.BadRequestException;
import com.bookverse.bookverse_backend.exception.ResourceNotFoundException;
import com.bookverse.bookverse_backend.repository.UserRepository;
import com.bookverse.bookverse_backend.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserProfileDTO getProfile(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        return mapToDTO(user);
    }

    @Override
    public UserProfileDTO updateProfile(
            String email,
            UpdateProfileDTO request) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        user.setName(request.getName());

        userRepository.save(user);

        return mapToDTO(user);
    }

    @Override
    public void changePassword(
            String email,
            ChangePasswordDTO request) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        if (!passwordEncoder.matches(
                request.getOldPassword(),
                user.getPassword())) {

            throw new BadRequestException("Old password is incorrect");
        }

        user.setPassword(
                passwordEncoder.encode(request.getNewPassword())
        );

        userRepository.save(user);
    }

    private UserProfileDTO mapToDTO(User user) {

        UserProfileDTO dto = new UserProfileDTO();

        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());

        // Convert enum to String
        dto.setRole(user.getRole().name());

        return dto;
    }
}