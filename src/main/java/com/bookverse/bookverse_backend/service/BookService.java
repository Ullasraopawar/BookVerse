package com.bookverse.bookverse_backend.service;

import com.bookverse.bookverse_backend.dto.BookRequestDTO;
import com.bookverse.bookverse_backend.dto.BookResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface BookService {

    BookResponseDTO addBook(BookRequestDTO requestDTO);

    Page<BookResponseDTO> getAllBooks(int page, int size, String sortBy);

    BookResponseDTO getBookById(Long id);

    BookResponseDTO updateBook(Long id, BookRequestDTO requestDTO);

    void deleteBook(Long id);

    List<BookResponseDTO> searchByTitle(String title);

    List<BookResponseDTO> searchByAuthor(String author);

    List<BookResponseDTO> searchByGenre(String genre);

    // NEW
    BookResponseDTO uploadBookCover(
            Long id,
            MultipartFile file
    ) throws IOException;
}