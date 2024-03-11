package com.example.webbookingroom.controller;

import com.example.webbookingroom.dto.CoRegisterDTO;
import com.example.webbookingroom.service.HotelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/hotel")
public class HotelController {
    private final HotelService hotelService;

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteHotel(@RequestParam Long id) {
        return hotelService.deleteHotel(id);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addHotelToUser(@RequestParam Long hotelId,
                                            @RequestParam Long userId) {
        return hotelService.addHotelToCoUser(userId, hotelId);
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllHotel() {
        return hotelService.getAllHotel();
    }
}
