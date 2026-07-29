package com.bookverse.bookverse_backend.controller;

import com.bookverse.bookverse_backend.dto.FavoriteRequestDTO;
import com.bookverse.bookverse_backend.dto.FavoriteResponseDTO;
import com.bookverse.bookverse_backend.service.FavoriteService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/favorites")
public class FavoriteController {

    private final FavoriteService favoriteService;

    public FavoriteController(FavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }

    @PostMapping
    public FavoriteResponseDTO addFavorite(
            @RequestBody FavoriteRequestDTO request,
            Authentication authentication) {

        return favoriteService.addFavorite(
                request,
                authentication.getName()
        );
    }

    @GetMapping
    public List<FavoriteResponseDTO> getFavorites(
            Authentication authentication) {

        return favoriteService.getFavorites(
                authentication.getName()
        );
    }

    @DeleteMapping("/{bookId}")
    public String removeFavorite(
            @PathVariable Long bookId,
            Authentication authentication) {

        favoriteService.removeFavorite(
                bookId,
                authentication.getName()
        );

        return "Book removed from favorites.";
    }
}