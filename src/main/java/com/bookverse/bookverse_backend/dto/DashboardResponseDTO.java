package com.bookverse.bookverse_backend.dto;

public class DashboardResponseDTO {

    private long totalBooks;
    private long favorites;
    private long reviewsWritten;
    private long wantToRead;
    private long currentlyReading;
    private long completed;

    public DashboardResponseDTO() {
    }

    public long getTotalBooks() {
        return totalBooks;
    }

    public void setTotalBooks(long totalBooks) {
        this.totalBooks = totalBooks;
    }

    public long getFavorites() {
        return favorites;
    }

    public void setFavorites(long favorites) {
        this.favorites = favorites;
    }

    public long getReviewsWritten() {
        return reviewsWritten;
    }

    public void setReviewsWritten(long reviewsWritten) {
        this.reviewsWritten = reviewsWritten;
    }

    public long getWantToRead() {
        return wantToRead;
    }

    public void setWantToRead(long wantToRead) {
        this.wantToRead = wantToRead;
    }

    public long getCurrentlyReading() {
        return currentlyReading;
    }

    public void setCurrentlyReading(long currentlyReading) {
        this.currentlyReading = currentlyReading;
    }

    public long getCompleted() {
        return completed;
    }

    public void setCompleted(long completed) {
        this.completed = completed;
    }
}