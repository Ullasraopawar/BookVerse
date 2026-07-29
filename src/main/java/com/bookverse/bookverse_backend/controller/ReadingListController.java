package com.bookverse.bookverse_backend.controller;

import com.bookverse.bookverse_backend.dto.ReadingListRequestDTO;
import com.bookverse.bookverse_backend.dto.ReadingListResponseDTO;
import com.bookverse.bookverse_backend.service.ReadingListService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reading-list")
public class ReadingListController {

    private final ReadingListService readingListService;

    public ReadingListController(ReadingListService readingListService) {
        this.readingListService = readingListService;
    }

    @PostMapping
    public ReadingListResponseDTO addBook(
            @RequestBody ReadingListRequestDTO request,
            Authentication authentication) {

        return readingListService.addBook(
                request,
                authentication.getName());
    }

    @GetMapping
    public List<ReadingListResponseDTO> getReadingList(
            Authentication authentication) {

        return readingListService.getMyReadingList(
                authentication.getName());
    }

    @PutMapping("/{id}")
    public ReadingListResponseDTO updateStatus(
            @PathVariable Long id,
            @RequestBody ReadingListRequestDTO request,
            Authentication authentication) {

        return readingListService.updateStatus(
                id,
                request,
                authentication.getName());
    }

    @DeleteMapping("/{id}")
    public String deleteBook(
            @PathVariable Long id,
            Authentication authentication) {

        readingListService.deleteBook(
                id,
                authentication.getName());

        return "Book removed from reading list.";
    }
}