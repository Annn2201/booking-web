package com.example.webbookingroom.service;

import com.example.webbookingroom.dto.CreateRoomDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;

public interface RoomService {
    ResponseEntity<?> createRoom(CreateRoomDto createRoomDto, Long hotelId);
    ResponseEntity<?> findRooms(int currentPage, Long hotelId, LocalDate checkIn, LocalDate checkOut, HttpServletRequest request);
}
