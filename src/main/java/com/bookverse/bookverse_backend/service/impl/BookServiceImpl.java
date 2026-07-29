package com.bookverse.bookverse_backend.service.impl;

import com.bookverse.bookverse_backend.dto.BookRequestDTO;
import com.bookverse.bookverse_backend.dto.BookResponseDTO;
import com.bookverse.bookverse_backend.entity.Book;
import com.bookverse.bookverse_backend.exception.BadRequestException;
import com.bookverse.bookverse_backend.exception.ResourceNotFoundException;
import com.bookverse.bookverse_backend.repository.BookRepository;
import com.bookverse.bookverse_backend.service.BookService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    @Value("${file.upload-dir}")
    private String uploadDir;
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public BookResponseDTO addBook(BookRequestDTO requestDTO) {

        if (bookRepository.existsByIsbn(requestDTO.getIsbn())) {
            throw new BadRequestException("Book with this ISBN already exists.");
        }

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
                .orElseThrow(() ->
                        new ResourceNotFoundException("Book not found with id : " + id));

        return mapToResponseDTO(book);
    }

    @Override
    public BookResponseDTO updateBook(Long id, BookRequestDTO requestDTO) {

        Book book = bookRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Book not found with id : " + id));

        if (!book.getIsbn().equals(requestDTO.getIsbn())
                && bookRepository.existsByIsbn(requestDTO.getIsbn())) {

            throw new BadRequestException("Another book already uses this ISBN.");
        }

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

        Book book = bookRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Book not found with id : " + id));

        bookRepository.delete(book);
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
    @Override
    public BookResponseDTO uploadBookCover(Long id, MultipartFile file)
            throws IOException {

        Book book = bookRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Book not found with id : " + id));

        if (file.isEmpty()) {
            throw new BadRequestException("Please select an image.");
        }

        String originalFileName = file.getOriginalFilename();

        if (originalFileName == null) {
            throw new BadRequestException("Invalid file.");
        }

        String lowerCaseName = originalFileName.toLowerCase();

        if (!(lowerCaseName.endsWith(".jpg")
                || lowerCaseName.endsWith(".jpeg")
                || lowerCaseName.endsWith(".png"))) {

            throw new BadRequestException(
                    "Only JPG and PNG images are allowed.");
        }


        Path uploadPath = Paths.get(uploadDir);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String fileName = UUID.randomUUID() + "_" + originalFileName;

        Path filePath = uploadPath.resolve(fileName);

        Files.copy(
                file.getInputStream(),
                filePath,
                StandardCopyOption.REPLACE_EXISTING
        );

        book.setCoverImage(fileName);

        Book savedBook = bookRepository.save(book);

        return mapToResponseDTO(savedBook);
    }

    private BookResponseDTO mapToResponseDTO(Book book) {

        BookResponseDTO dto = new BookResponseDTO();

        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setAuthor(book.getAuthor());
        dto.setGenre(book.getGenre());
        dto.setPublishedYear(book.getPublishedYear());
        dto.setIsbn(book.getIsbn());
        dto.setCoverImage(book.getCoverImage());

        int reviewCount = book.getReviews().size();
        dto.setReviewCount(reviewCount);

        if (reviewCount == 0) {

            dto.setAverageRating(0.0);

        } else {

            double average = book.getReviews()
                    .stream()
                    .mapToInt(review -> review.getRating())
                    .average()
                    .orElse(0.0);

            dto.setAverageRating(Math.round(average * 10.0) / 10.0);
        }

        return dto;
    }
}