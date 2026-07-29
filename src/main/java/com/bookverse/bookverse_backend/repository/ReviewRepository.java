package com.bookverse.bookverse_backend.repository;

import com.bookverse.bookverse_backend.entity.Review;
import com.bookverse.bookverse_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByBookId(Long bookId);

    long countByUser(User user);
}