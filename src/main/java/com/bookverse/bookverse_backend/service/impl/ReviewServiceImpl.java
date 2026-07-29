package com.bookverse.bookverse_backend.service.impl;

import com.bookverse.bookverse_backend.dto.ReviewRequestDTO;
import com.bookverse.bookverse_backend.dto.ReviewResponseDTO;
import com.bookverse.bookverse_backend.entity.Book;
import com.bookverse.bookverse_backend.entity.Review;
import com.bookverse.bookverse_backend.entity.User;
import com.bookverse.bookverse_backend.repository.BookRepository;
import com.bookverse.bookverse_backend.repository.ReviewRepository;
import com.bookverse.bookverse_backend.repository.UserRepository;
import com.bookverse.bookverse_backend.service.ReviewService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository,
                             BookRepository bookRepository,
                             UserRepository userRepository) {
        this.reviewRepository = reviewRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ReviewResponseDTO addReview(ReviewRequestDTO request, String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Book book = bookRepository.findById(request.getBookId())
                .orElseThrow(() -> new RuntimeException("Book not found"));

        Review review = new Review();

        review.setBook(book);
        review.setUser(user);
        review.setRating(request.getRating());
        review.setComment(request.getComment());

        Review savedReview = reviewRepository.save(review);

        return mapToDTO(savedReview);
    }

    @Override
    public List<ReviewResponseDTO> getReviewsByBook(Long bookId) {

        return reviewRepository.findByBookId(bookId)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    public ReviewResponseDTO updateReview(Long reviewId,
                                          ReviewRequestDTO request,
                                          String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found"));

        if (!review.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("You can update only your own review.");
        }

        review.setRating(request.getRating());
        review.setComment(request.getComment());

        Review updated = reviewRepository.save(review);

        return mapToDTO(updated);
    }

    @Override
    public void deleteReview(Long reviewId, String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found"));

        if (!review.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("You can delete only your own review.");
        }

        reviewRepository.delete(review);
    }

    private ReviewResponseDTO mapToDTO(Review review) {

        ReviewResponseDTO dto = new ReviewResponseDTO();

        dto.setId(review.getId());
        dto.setBookId(review.getBook().getId());
        dto.setBookTitle(review.getBook().getTitle());
        dto.setReviewerName(review.getUser().getName());
        dto.setRating(review.getRating());
        dto.setComment(review.getComment());
        dto.setCreatedAt(review.getCreatedAt());

        return dto;
    }
}