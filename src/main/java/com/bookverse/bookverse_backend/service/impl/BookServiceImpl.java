package com.bookverse.bookverse_backend.service.impl;

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
    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Book getBookById(Long id) {
        return bookRepository.findById(id).orElse(null);
    }

    @Override
    public Book updateBook(Long id, Book book) {

        Book existingBook = bookRepository.findById(id).orElse(null);

        if (existingBook != null) {

            existingBook.setTitle(book.getTitle());
            existingBook.setAuthor(book.getAuthor());
            existingBook.setGenre(book.getGenre());
            existingBook.setPublishedYear(book.getPublishedYear());
            existingBook.setIsbn(book.getIsbn());

            return bookRepository.save(existingBook);
        }

        return null;
    }

    @Override
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }
}