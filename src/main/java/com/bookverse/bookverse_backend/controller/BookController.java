package com.bookverse.bookverse_backend.controller;

import com.bookverse.bookverse_backend.dto.BookRequestDTO;
import com.bookverse.bookverse_backend.dto.BookResponseDTO;
import com.bookverse.bookverse_backend.service.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService bookService;

    // Add Book
    @PostMapping
    public BookResponseDTO addBook(@Valid @RequestBody BookRequestDTO requestDTO) {
        return bookService.addBook(requestDTO);
    }

    // Get All Books
    @GetMapping
    public List<BookResponseDTO> getAllBooks() {
        return bookService.getAllBooks();
    }

    // Get Book By Id
    @GetMapping("/{id}")
    public BookResponseDTO getBookById(@PathVariable Long id) {
        return bookService.getBookById(id);
    }

    // Update Book
    @PutMapping("/{id}")
    public BookResponseDTO updateBook(@PathVariable Long id,
                                      @Valid @RequestBody BookRequestDTO requestDTO) {
        return bookService.updateBook(id, requestDTO);
    }

    // Delete Book
    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
    }
}