package com.bookverse.bookverse_backend.dto;

public class FavoriteRequestDTO {

    private Long bookId;

    public FavoriteRequestDTO() {
    }

    public FavoriteRequestDTO(Long bookId) {
        this.bookId = bookId;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }
}