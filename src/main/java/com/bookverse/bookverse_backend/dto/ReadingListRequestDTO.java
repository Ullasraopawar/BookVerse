package com.bookverse.bookverse_backend.dto;

import com.bookverse.bookverse_backend.entity.ReadingStatus;

public class ReadingListRequestDTO {

    private Long bookId;
    private ReadingStatus status;

    public ReadingListRequestDTO() {
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public ReadingStatus getStatus() {
        return status;
    }

    public void setStatus(ReadingStatus status) {
        this.status = status;
    }
}