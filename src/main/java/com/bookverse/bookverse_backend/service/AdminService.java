package com.bookverse.bookverse_backend.service;

import com.bookverse.bookverse_backend.entity.Book;
import com.bookverse.bookverse_backend.entity.Review;
import com.bookverse.bookverse_backend.entity.User;

import java.util.List;

public interface AdminService {

    List<User> getAllUsers();

    List<Book> getAllBooks();

    List<Review> getAllReviews();

    void deleteUser(Long id);

    void deleteBook(Long id);

    void deleteReview(Long id);
}