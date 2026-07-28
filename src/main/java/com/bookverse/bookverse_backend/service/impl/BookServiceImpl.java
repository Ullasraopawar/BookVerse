package com.bookverse.bookverse_backend.service.impl;

import com.bookverse.bookverse_backend.dto.BookRequestDTO;
import com.bookverse.bookverse_backend.dto.BookResponseDTO;
import com.bookverse.bookverse_backend.entity.Book;
import com.bookverse.bookverse_backend.repository.BookRepository;
import com.bookverse.bookverse_backend.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
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

        BookResponseDTO responseDTO = new BookResponseDTO();

        responseDTO.setId(savedBook.getId());
        responseDTO.setTitle(savedBook.getTitle());
        responseDTO.setAuthor(savedBook.getAuthor());
        responseDTO.setGenre(savedBook.getGenre());
        responseDTO.setPublishedYear(savedBook.getPublishedYear());
        responseDTO.setIsbn(savedBook.getIsbn());

        return responseDTO;
    }

    @Override
    public List<BookResponseDTO> getAllBooks() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public BookResponseDTO getBookById(Long id) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public BookResponseDTO updateBook(Long id, BookRequestDTO requestDTO) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }
}