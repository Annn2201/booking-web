package com.example.webbookingroom.controller;

import com.example.webbookingroom.service.HotelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin")
public class AdminController {
    private final HotelService hotelService;

//    @GetMapping("/hotels")
//    public ResponseEntity<?> getHotels() {
//        hotelService.getAllHotel();
//    }
}
