package com.bookverse.bookverse_backend.controller;

import com.bookverse.bookverse_backend.dto.ReviewRequestDTO;
import com.bookverse.bookverse_backend.dto.ReviewResponseDTO;
import com.bookverse.bookverse_backend.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@CrossOrigin(origins = "*")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping
    public ReviewResponseDTO addReview(@RequestBody ReviewRequestDTO requestDTO) {
        return reviewService.addReview(requestDTO);
    }

    @GetMapping("/book/{bookId}")
    public List<ReviewResponseDTO> getReviewsByBook(@PathVariable Long bookId) {
        return reviewService.getReviewsByBook(bookId);
    }

    @PutMapping("/{id}")
    public ReviewResponseDTO updateReview(@PathVariable Long id,
                                          @RequestBody ReviewRequestDTO requestDTO) {
        return reviewService.updateReview(id, requestDTO);
    }

    @DeleteMapping("/{id}")
    public String deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
        return "Review deleted successfully";
    }
}