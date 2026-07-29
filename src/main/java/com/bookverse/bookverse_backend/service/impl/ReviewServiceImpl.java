package com.bookverse.bookverse_backend.service.impl;

import com.bookverse.bookverse_backend.dto.ReviewRequestDTO;
import com.bookverse.bookverse_backend.dto.ReviewResponseDTO;
import com.bookverse.bookverse_backend.entity.Book;
import com.bookverse.bookverse_backend.entity.Review;
import com.bookverse.bookverse_backend.repository.BookRepository;
import com.bookverse.bookverse_backend.repository.ReviewRepository;
import com.bookverse.bookverse_backend.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private BookRepository bookRepository;

    @Override
    public ReviewResponseDTO addReview(ReviewRequestDTO requestDTO) {

        Book book = bookRepository.findById(requestDTO.getBookId())
                .orElseThrow(() -> new RuntimeException("Book not found"));

        Review review = new Review();

        review.setRating(requestDTO.getRating());
        review.setReviewerName(requestDTO.getReviewerName());
        review.setComment(requestDTO.getComment());
        review.setCreatedAt(LocalDateTime.now());
        review.setBook(book);

        return mapToDTO(reviewRepository.save(review));
    }

    @Override
    public List<ReviewResponseDTO> getReviewsByBook(Long bookId) {

        return reviewRepository.findByBookId(bookId)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    public ReviewResponseDTO updateReview(Long id, ReviewRequestDTO requestDTO) {

        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found"));

        review.setRating(requestDTO.getRating());
        review.setReviewerName(requestDTO.getReviewerName());
        review.setComment(requestDTO.getComment());

        return mapToDTO(reviewRepository.save(review));
    }

    @Override
    public void deleteReview(Long id) {

        if (!reviewRepository.existsById(id)) {
            throw new RuntimeException("Review not found");
        }

        reviewRepository.deleteById(id);
    }

    private ReviewResponseDTO mapToDTO(Review review) {

        ReviewResponseDTO dto = new ReviewResponseDTO();

        dto.setId(review.getId());
        dto.setRating(review.getRating());
        dto.setReviewerName(review.getReviewerName());
        dto.setComment(review.getComment());
        dto.setCreatedAt(review.getCreatedAt());
        dto.setBookId(review.getBook().getId());

        return dto;
    }
}