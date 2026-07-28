package com.bookverse.bookverse_backend.repository;

import com.bookverse.bookverse_backend.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}