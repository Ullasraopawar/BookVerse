package com.bookverse.bookverse_backend.service.impl;

import com.bookverse.bookverse_backend.dto.ReadingListRequestDTO;
import com.bookverse.bookverse_backend.dto.ReadingListResponseDTO;
import com.bookverse.bookverse_backend.entity.Book;
import com.bookverse.bookverse_backend.entity.ReadingList;
import com.bookverse.bookverse_backend.entity.User;
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
                .orElseThrow(() -> new RuntimeException("User not found"));

        Book book = bookRepository.findById(request.getBookId())
                .orElseThrow(() -> new RuntimeException("Book not found"));

        if (readingListRepository
                .findByUserIdAndBookId(user.getId(), book.getId())
                .isPresent()) {

            throw new RuntimeException("Book already exists in reading list");
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
                .orElseThrow(() -> new RuntimeException("User not found"));

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
                .orElseThrow(() -> new RuntimeException("User not found"));

        ReadingList readingList = readingListRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reading list entry not found"));

        if (!readingList.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized");
        }

        readingList.setStatus(request.getStatus());

        return mapToDTO(readingListRepository.save(readingList));
    }

    @Override
    public void deleteBook(Long id, String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        ReadingList readingList = readingListRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reading list entry not found"));

        if (!readingList.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized");
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