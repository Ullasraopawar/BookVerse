package com.bookverse.bookverse_backend.service;

import com.bookverse.bookverse_backend.dto.ReviewRequestDTO;
import com.bookverse.bookverse_backend.dto.ReviewResponseDTO;

import java.util.List;

public interface ReviewService {

    ReviewResponseDTO addReview(
            ReviewRequestDTO request,
            String email);

    List<ReviewResponseDTO> getReviewsByBook(
            Long bookId);

    ReviewResponseDTO updateReview(
            Long reviewId,
            ReviewRequestDTO request,
            String email);

    void deleteReview(
            Long reviewId,
            String email);
}