package com.example.webbookingroom.service;

import com.example.webbookingroom.dto.CoRegisterDTO;
import com.example.webbookingroom.model.Location;
import org.springframework.http.ResponseEntity;

public interface HotelService {
    ResponseEntity<?> createHotel(CoRegisterDTO coRegisterDTO);
    ResponseEntity<?> deleteHotel(Long id);
    ResponseEntity<?> addHotelToUser(CoRegisterDTO coRegisterDTO, Long userId);

}

