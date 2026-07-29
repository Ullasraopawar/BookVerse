package com.bookverse.bookverse_backend.controller;

import com.bookverse.bookverse_backend.dto.DashboardResponseDTO;
import com.bookverse.bookverse_backend.service.DashboardService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin(origins = "*")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping
    public DashboardResponseDTO getDashboard(
            Authentication authentication) {

        return dashboardService.getDashboard(
                authentication.getName()
        );
    }
}