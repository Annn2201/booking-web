package com.example.webbookingroom.service;

import com.example.webbookingroom.dto.CoRegisterDTO;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;

public interface HotelService {
    ResponseEntity<?> getAllHotel();
    ResponseEntity<?> findSuitableHotel(String location, String hotelType, LocalDate checkIn, LocalDate checkOut);
    ResponseEntity<?> createHotel(CoRegisterDTO coRegisterDTO);
    ResponseEntity<?> deleteHotel(Long id);
    ResponseEntity<?> addHotelToCoUser(Long userId, Long hotelId);

}

