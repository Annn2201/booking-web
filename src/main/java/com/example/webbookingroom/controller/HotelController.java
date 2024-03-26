package com.example.webbookingroom.controller;

import com.example.webbookingroom.dto.CoRegisterDTO;
import com.example.webbookingroom.dto.response.BookerRequest;
import com.example.webbookingroom.service.HotelService;
import com.example.webbookingroom.service.RoomService;
import com.example.webbookingroom.service.TypeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/hotel")
@CrossOrigin(origins="*")
public class HotelController {
    private final HotelService hotelService;
    private final TypeService typeService;
    private final RoomService roomService;

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteHotel(@PathVariable String id) {
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

    @GetMapping("/get-type")
    public ResponseEntity<?> getAllType() {
        return typeService.getAllType();
    }

    @GetMapping("/{hotelName}")
    public ResponseEntity<?> getHotelDetail(@PathVariable String hotelName) {
        return hotelService.findHotelByHotelName(hotelName);
    }

    @GetMapping("/rooms/{hotelName}")
    public ResponseEntity<?> getRooms(@PathVariable String hotelName) {
        return roomService.findRooms(hotelName);
    }

    @PostMapping("/book")
    public ResponseEntity<?> bookRoom(
            @RequestBody BookerRequest request){
        return roomService.bookRooms(request);
    }
}
