package com.bookverse.bookverse_backend.service.impl;

import com.bookverse.bookverse_backend.dto.ReadingListRequestDTO;
import com.bookverse.bookverse_backend.dto.ReadingListResponseDTO;
import com.bookverse.bookverse_backend.entity.Book;
import com.bookverse.bookverse_backend.entity.ReadingList;
import com.bookverse.bookverse_backend.entity.User;
import com.bookverse.bookverse_backend.exception.BadRequestException;
import com.bookverse.bookverse_backend.exception.ResourceNotFoundException;
import com.bookverse.bookverse_backend.repository.BookRepository;
import com.bookverse.bookverse_backend.repository.ReadingListRepository;
import com.bookverse.bookverse_backend.repository.UserRepository;
import com.bookverse.bookverse_backend.service.ReadingListService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReadingListServiceImpl implements ReadingListService {

    private final ReadingListRepository readingListRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    public ReadingListServiceImpl(
            ReadingListRepository readingListRepository,
            UserRepository userRepository,
            BookRepository bookRepository) {

        this.readingListRepository = readingListRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public ReadingListResponseDTO addBook(
            ReadingListRequestDTO request,
            String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        Book book = bookRepository.findById(request.getBookId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Book not found"));

        if (readingListRepository
                .findByUserIdAndBookId(user.getId(), book.getId())
                .isPresent()) {

            throw new BadRequestException(
                    "Book already exists in your reading list.");
        }

        ReadingList readingList = new ReadingList();
        readingList.setUser(user);
        readingList.setBook(book);
        readingList.setStatus(request.getStatus());

        ReadingList saved = readingListRepository.save(readingList);

        return mapToDTO(saved);
    }

    @Override
    public List<ReadingListResponseDTO> getMyReadingList(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        return readingListRepository.findByUser(user)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    public ReadingListResponseDTO updateStatus(
            Long id,
            ReadingListRequestDTO request,
            String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        ReadingList readingList = readingListRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Reading list entry not found"));

        if (!readingList.getUser().getId().equals(user.getId())) {
            throw new BadRequestException(
                    "You can update only your own reading list.");
        }

        readingList.setStatus(request.getStatus());

        ReadingList updated = readingListRepository.save(readingList);

        return mapToDTO(updated);
    }

    @Override
    public void deleteBook(Long id, String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        ReadingList readingList = readingListRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Reading list entry not found"));

        if (!readingList.getUser().getId().equals(user.getId())) {
            throw new BadRequestException(
                    "You can delete only your own reading list.");
        }

        readingListRepository.delete(readingList);
    }

    private ReadingListResponseDTO mapToDTO(ReadingList readingList) {

        ReadingListResponseDTO dto = new ReadingListResponseDTO();

        dto.setId(readingList.getId());
        dto.setBookId(readingList.getBook().getId());
        dto.setTitle(readingList.getBook().getTitle());
        dto.setAuthor(readingList.getBook().getAuthor());
        dto.setStatus(readingList.getStatus());

        return dto;
    }
}