package com.bookverse.bookverse_backend.service;

import com.bookverse.bookverse_backend.dto.ReviewRequestDTO;
import com.bookverse.bookverse_backend.dto.ReviewResponseDTO;

import java.util.List;

public interface ReviewService {

    ReviewResponseDTO addReview(ReviewRequestDTO requestDTO);

    List<ReviewResponseDTO> getReviewsByBook(Long bookId);

    ReviewResponseDTO updateReview(Long id, ReviewRequestDTO requestDTO);

    void deleteReview(Long id);
}