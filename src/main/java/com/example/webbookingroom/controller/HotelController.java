package com.example.webbookingroom.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class HotelController {
    @GetMapping("/hotels")
    public ResponseEntity<?> findSuitableHotel() {
        return ResponseEntity.ok("ok");
    }
}
