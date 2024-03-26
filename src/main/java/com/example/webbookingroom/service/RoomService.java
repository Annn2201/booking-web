package com.example.webbookingroom.service;

import com.example.webbookingroom.dto.CreateRoomDto;
import com.example.webbookingroom.dto.response.BookerRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;

public interface RoomService {
    ResponseEntity<?> createRoom(CreateRoomDto createRoomDto, Long hotelId);
    ResponseEntity<?> findRooms(String hotelName);
    ResponseEntity<?> bookRooms(BookerRequest request) ;
}
