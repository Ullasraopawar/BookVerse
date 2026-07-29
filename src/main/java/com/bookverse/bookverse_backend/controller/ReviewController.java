package com.bookverse.bookverse_backend.controller;

import com.bookverse.bookverse_backend.dto.ReviewRequestDTO;
import com.bookverse.bookverse_backend.dto.ReviewResponseDTO;
import com.bookverse.bookverse_backend.service.ReviewService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@CrossOrigin(origins = "*")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping
    public ReviewResponseDTO addReview(
            @RequestBody ReviewRequestDTO request,
            Authentication authentication) {

        return reviewService.addReview(
                request,
                authentication.getName()
        );
    }

    @GetMapping("/book/{bookId}")
    public List<ReviewResponseDTO> getReviewsByBook(
            @PathVariable Long bookId) {

        return reviewService.getReviewsByBook(bookId);
    }

    @PutMapping("/{reviewId}")
    public ReviewResponseDTO updateReview(
            @PathVariable Long reviewId,
            @RequestBody ReviewRequestDTO request,
            Authentication authentication) {

        return reviewService.updateReview(
                reviewId,
                request,
                authentication.getName()
        );
    }

    @DeleteMapping("/{reviewId}")
    public String deleteReview(
            @PathVariable Long reviewId,
            Authentication authentication) {

        reviewService.deleteReview(
                reviewId,
                authentication.getName()
        );

        return "Review deleted successfully.";
    }
}