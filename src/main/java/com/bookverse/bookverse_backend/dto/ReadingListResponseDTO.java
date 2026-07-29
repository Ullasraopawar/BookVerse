package com.bookverse.bookverse_backend.dto;

import com.bookverse.bookverse_backend.entity.ReadingStatus;

public class ReadingListResponseDTO {

    private Long id;
    private Long bookId;
    private String title;
    private String author;
    private ReadingStatus status;

    public ReadingListResponseDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public ReadingStatus getStatus() {
        return status;
    }

    public void setStatus(ReadingStatus status) {
        this.status = status;
    }
}