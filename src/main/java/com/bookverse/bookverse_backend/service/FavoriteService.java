package com.bookverse.bookverse_backend.service;

import com.bookverse.bookverse_backend.dto.FavoriteRequestDTO;
import com.bookverse.bookverse_backend.dto.FavoriteResponseDTO;

import java.util.List;

public interface FavoriteService {

    FavoriteResponseDTO addFavorite(
            FavoriteRequestDTO request,
            String email
    );

    List<FavoriteResponseDTO> getFavorites(
            String email
    );

    void removeFavorite(
            Long bookId,
            String email
    );
}