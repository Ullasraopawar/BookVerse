package com.bookverse.bookverse_backend.repository;

import com.bookverse.bookverse_backend.entity.ReadingList;
import com.bookverse.bookverse_backend.entity.ReadingStatus;
import com.bookverse.bookverse_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReadingListRepository extends JpaRepository<ReadingList, Long> {

    List<ReadingList> findByUser(User user);

    Optional<ReadingList> findByUserIdAndBookId(Long userId, Long bookId);

    long countByUser(User user);

    long countByUserAndStatus(User user, ReadingStatus status);
}