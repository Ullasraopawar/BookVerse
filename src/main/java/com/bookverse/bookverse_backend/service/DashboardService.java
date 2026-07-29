package com.bookverse.bookverse_backend.service;

import com.bookverse.bookverse_backend.dto.DashboardResponseDTO;

public interface DashboardService {

    DashboardResponseDTO getDashboard(String email);

}