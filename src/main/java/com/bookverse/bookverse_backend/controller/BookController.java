package com.bookverse.bookverse_backend.controller;

import com.bookverse.bookverse_backend.dto.BookRequestDTO;
import com.bookverse.bookverse_backend.dto.BookResponseDTO;
import com.bookverse.bookverse_backend.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@CrossOrigin(origins = "*")
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping
    public BookResponseDTO addBook(@RequestBody BookRequestDTO requestDTO) {
        return bookService.addBook(requestDTO);
    }

    @GetMapping
    public Page<BookResponseDTO> getAllBooks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "title") String sortBy) {

        return bookService.getAllBooks(page, size, sortBy);
    }

    @GetMapping("/{id}")
    public BookResponseDTO getBookById(@PathVariable Long id) {
        return bookService.getBookById(id);
    }

    @PutMapping("/{id}")
    public BookResponseDTO updateBook(
            @PathVariable Long id,
            @RequestBody BookRequestDTO requestDTO) {

        return bookService.updateBook(id, requestDTO);
    }

    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable Long id) {

        bookService.deleteBook(id);

        return "Book deleted successfully.";
    }

    @GetMapping("/search/title")
    public List<BookResponseDTO> searchByTitle(
            @RequestParam String title) {

        return bookService.searchByTitle(title);
    }

    @GetMapping("/search/author")
    public List<BookResponseDTO> searchByAuthor(
            @RequestParam String author) {

        return bookService.searchByAuthor(author);
    }

    @GetMapping("/search/genre")
    public List<BookResponseDTO> searchByGenre(
            @RequestParam String genre) {

        return bookService.searchByGenre(genre);
    }
}