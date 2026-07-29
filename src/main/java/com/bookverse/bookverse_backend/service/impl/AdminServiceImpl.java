package com.bookverse.bookverse_backend.service.impl;

import com.bookverse.bookverse_backend.entity.Book;
import com.bookverse.bookverse_backend.entity.Review;
import com.bookverse.bookverse_backend.entity.User;
import com.bookverse.bookverse_backend.exception.ResourceNotFoundException;
import com.bookverse.bookverse_backend.repository.BookRepository;
import com.bookverse.bookverse_backend.repository.ReviewRepository;
import com.bookverse.bookverse_backend.repository.UserRepository;
import com.bookverse.bookverse_backend.service.AdminService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final ReviewRepository reviewRepository;

    public AdminServiceImpl(UserRepository userRepository,
                            BookRepository bookRepository,
                            ReviewRepository reviewRepository) {

        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
        this.reviewRepository = reviewRepository;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    @Override
    public void deleteUser(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found with id : " + id));

        userRepository.delete(user);
    }

    @Override
    public void deleteBook(Long id) {

        Book book = bookRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Book not found with id : " + id));

        bookRepository.delete(book);
    }

    @Override
    public void deleteReview(Long id) {

        Review review = reviewRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Review not found with id : " + id));

        reviewRepository.delete(review);
    }
}