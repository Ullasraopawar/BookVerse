package com.bookverse.bookverse_backend.service;

import com.bookverse.bookverse_backend.dto.ReadingListRequestDTO;
import com.bookverse.bookverse_backend.dto.ReadingListResponseDTO;

import java.util.List;

public interface ReadingListService {

    ReadingListResponseDTO addBook(
            ReadingListRequestDTO request,
            String email
    );

    List<ReadingListResponseDTO> getMyReadingList(
            String email
    );

    ReadingListResponseDTO updateStatus(
            Long id,
            ReadingListRequestDTO request,
            String email
    );

    void deleteBook(
            Long id,
            String email
    );
}