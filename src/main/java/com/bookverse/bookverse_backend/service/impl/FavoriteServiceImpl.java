package com.bookverse.bookverse_backend.service.impl;

import com.bookverse.bookverse_backend.dto.FavoriteRequestDTO;
import com.bookverse.bookverse_backend.dto.FavoriteResponseDTO;
import com.bookverse.bookverse_backend.entity.Book;
import com.bookverse.bookverse_backend.entity.Favorite;
import com.bookverse.bookverse_backend.entity.User;
import com.bookverse.bookverse_backend.exception.BadRequestException;
import com.bookverse.bookverse_backend.exception.ResourceNotFoundException;
import com.bookverse.bookverse_backend.repository.BookRepository;
import com.bookverse.bookverse_backend.repository.FavoriteRepository;
import com.bookverse.bookverse_backend.repository.UserRepository;
import com.bookverse.bookverse_backend.service.FavoriteService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavoriteServiceImpl implements FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    public FavoriteServiceImpl(FavoriteRepository favoriteRepository,
                               UserRepository userRepository,
                               BookRepository bookRepository) {
        this.favoriteRepository = favoriteRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public FavoriteResponseDTO addFavorite(FavoriteRequestDTO request,
                                           String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        Book book = bookRepository.findById(request.getBookId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Book not found"));

        if (favoriteRepository.findByUserIdAndBookId(user.getId(), book.getId()).isPresent()) {
            throw new BadRequestException("Book is already in your favorites.");
        }

        Favorite favorite = new Favorite();
        favorite.setUser(user);
        favorite.setBook(book);

        Favorite saved = favoriteRepository.save(favorite);

        return mapToDTO(saved);
    }

    @Override
    public List<FavoriteResponseDTO> getFavorites(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        return favoriteRepository.findByUser(user)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    public void removeFavorite(Long bookId, String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        Favorite favorite = favoriteRepository
                .findByUserIdAndBookId(user.getId(), bookId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Favorite not found"));

        favoriteRepository.delete(favorite);
    }

    private FavoriteResponseDTO mapToDTO(Favorite favorite) {

        FavoriteResponseDTO dto = new FavoriteResponseDTO();

        dto.setFavoriteId(favorite.getId());
        dto.setBookId(favorite.getBook().getId());
        dto.setTitle(favorite.getBook().getTitle());
        dto.setAuthor(favorite.getBook().getAuthor());
        dto.setGenre(favorite.getBook().getGenre());

        return dto;
    }
}