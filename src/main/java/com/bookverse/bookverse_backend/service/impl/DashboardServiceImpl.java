package com.bookverse.bookverse_backend.service.impl;

import com.bookverse.bookverse_backend.dto.DashboardResponseDTO;
import com.bookverse.bookverse_backend.entity.ReadingStatus;
import com.bookverse.bookverse_backend.entity.User;
import com.bookverse.bookverse_backend.repository.BookRepository;
import com.bookverse.bookverse_backend.repository.FavoriteRepository;
import com.bookverse.bookverse_backend.repository.ReadingListRepository;
import com.bookverse.bookverse_backend.repository.ReviewRepository;
import com.bookverse.bookverse_backend.repository.UserRepository;
import com.bookverse.bookverse_backend.service.DashboardService;
import org.springframework.stereotype.Service;

@Service
public class DashboardServiceImpl implements DashboardService {

    private final BookRepository bookRepository;
    private final FavoriteRepository favoriteRepository;
    private final ReviewRepository reviewRepository;
    private final ReadingListRepository readingListRepository;
    private final UserRepository userRepository;

    public DashboardServiceImpl(BookRepository bookRepository,
                                FavoriteRepository favoriteRepository,
                                ReviewRepository reviewRepository,
                                ReadingListRepository readingListRepository,
                                UserRepository userRepository) {

        this.bookRepository = bookRepository;
        this.favoriteRepository = favoriteRepository;
        this.reviewRepository = reviewRepository;
        this.readingListRepository = readingListRepository;
        this.userRepository = userRepository;
    }

    @Override
    public DashboardResponseDTO getDashboard(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        DashboardResponseDTO dto = new DashboardResponseDTO();

        dto.setTotalBooks(bookRepository.count());

        dto.setFavorites(
                favoriteRepository.countByUser(user)
        );

        dto.setReviewsWritten(
                reviewRepository.countByUser(user)
        );

        dto.setWantToRead(
                readingListRepository.countByUserAndStatus(
                        user,
                        ReadingStatus.WANT_TO_READ
                )
        );

        dto.setCurrentlyReading(
                readingListRepository.countByUserAndStatus(
                        user,
                        ReadingStatus.CURRENTLY_READING
                )
        );

        dto.setCompleted(
                readingListRepository.countByUserAndStatus(
                        user,
                        ReadingStatus.COMPLETED
                )
        );

        return dto;
    }
}