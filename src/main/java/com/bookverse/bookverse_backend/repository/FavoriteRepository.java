package com.bookverse.bookverse_backend.repository;

import com.bookverse.bookverse_backend.entity.Favorite;
import com.bookverse.bookverse_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

    List<Favorite> findByUser(User user);

    Optional<Favorite> findByUserIdAndBookId(Long userId, Long bookId);

    long countByUser(User user);
}