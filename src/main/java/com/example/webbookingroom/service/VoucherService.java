package com.example.webbookingroom.service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

public interface VoucherService {
    ResponseEntity<?> findVouchers(int currentPage, String hotelName, HttpServletRequest request);
}
