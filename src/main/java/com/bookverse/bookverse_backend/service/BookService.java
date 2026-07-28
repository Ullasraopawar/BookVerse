package com.bookverse.bookverse_backend.service;

import com.bookverse.bookverse_backend.dto.BookRequestDTO;
import com.bookverse.bookverse_backend.dto.BookResponseDTO;
import com.bookverse.bookverse_backend.dto.BookRequestDTO;
import com.bookverse.bookverse_backend.dto.BookResponseDTO;

import java.util.List;

public interface BookService {

    BookResponseDTO addBook(BookRequestDTO requestDTO);

    List<BookResponseDTO> getAllBooks();

    BookResponseDTO getBookById(Long id);

    BookResponseDTO updateBook(Long id, BookRequestDTO requestDTO);

    void deleteBook(Long id);
}