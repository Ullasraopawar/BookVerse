package com.bookverse.bookverse_backend.controller;

import com.bookverse.bookverse_backend.entity.Book;
import com.bookverse.bookverse_backend.entity.Review;
import com.bookverse.bookverse_backend.entity.User;
import com.bookverse.bookverse_backend.service.AdminService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return adminService.getAllUsers();
    }

    @GetMapping("/books")
    public List<Book> getAllBooks() {
        return adminService.getAllBooks();
    }

    @GetMapping("/reviews")
    public List<Review> getAllReviews() {
        return adminService.getAllReviews();
    }

    @DeleteMapping("/users/{id}")
    public String deleteUser(@PathVariable Long id) {

        adminService.deleteUser(id);

        return "User deleted successfully.";
    }

    @DeleteMapping("/books/{id}")
    public String deleteBook(@PathVariable Long id) {

        adminService.deleteBook(id);

        return "Book deleted successfully.";
    }

    @DeleteMapping("/reviews/{id}")
    public String deleteReview(@PathVariable Long id) {

        adminService.deleteReview(id);

        return "Review deleted successfully.";
    }
}