package com.bookverse.bookverse_backend.service.impl;

import com.bookverse.bookverse_backend.dto.BookRequestDTO;
import com.bookverse.bookverse_backend.dto.BookResponseDTO;
import com.bookverse.bookverse_backend.entity.Book;
import com.bookverse.bookverse_backend.repository.BookRepository;
import com.bookverse.bookverse_backend.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Override
    public BookResponseDTO addBook(BookRequestDTO requestDTO) {

        Book book = new Book();

        book.setTitle(requestDTO.getTitle());
        book.setAuthor(requestDTO.getAuthor());
        book.setGenre(requestDTO.getGenre());
        book.setPublishedYear(requestDTO.getPublishedYear());
        book.setIsbn(requestDTO.getIsbn());

        Book savedBook = bookRepository.save(book);

        return mapToResponseDTO(savedBook);
    }

    @Override
    public Page<BookResponseDTO> getAllBooks(int page, int size, String sortBy) {

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(sortBy).ascending()
        );

        return bookRepository.findAll(pageable)
                .map(this::mapToResponseDTO);
    }

    @Override
    public BookResponseDTO getBookById(Long id) {

        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        return mapToResponseDTO(book);
    }

    @Override
    public BookResponseDTO updateBook(Long id, BookRequestDTO requestDTO) {

        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        book.setTitle(requestDTO.getTitle());
        book.setAuthor(requestDTO.getAuthor());
        book.setGenre(requestDTO.getGenre());
        book.setPublishedYear(requestDTO.getPublishedYear());
        book.setIsbn(requestDTO.getIsbn());

        Book updatedBook = bookRepository.save(book);

        return mapToResponseDTO(updatedBook);
    }

    @Override
    public void deleteBook(Long id) {

        if (!bookRepository.existsById(id)) {
            throw new RuntimeException("Book not found");
        }

        bookRepository.deleteById(id);
    }

    @Override
    public List<BookResponseDTO> searchByTitle(String title) {

        return bookRepository.findByTitleContainingIgnoreCase(title)
                .stream()
                .map(this::mapToResponseDTO)
                .toList();
    }

    @Override
    public List<BookResponseDTO> searchByAuthor(String author) {

        return bookRepository.findByAuthorContainingIgnoreCase(author)
                .stream()
                .map(this::mapToResponseDTO)
                .toList();
    }

    @Override
    public List<BookResponseDTO> searchByGenre(String genre) {

        return bookRepository.findByGenreContainingIgnoreCase(genre)
                .stream()
                .map(this::mapToResponseDTO)
                .toList();
    }

    private BookResponseDTO mapToResponseDTO(Book book) {

        BookResponseDTO dto = new BookResponseDTO();

        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setAuthor(book.getAuthor());
        dto.setGenre(book.getGenre());
        dto.setPublishedYear(book.getPublishedYear());
        dto.setIsbn(book.getIsbn());

        int reviewCount = book.getReviews().size();
        dto.setReviewCount(reviewCount);

        if (reviewCount == 0) {

            dto.setAverageRating(0.0);

        } else {

            double averageRating = book.getReviews()
                    .stream()
                    .mapToInt(review -> review.getRating())
                    .average()
                    .orElse(0.0);

            dto.setAverageRating(Math.round(averageRating * 10.0) / 10.0);
        }

        return dto;
    }
}