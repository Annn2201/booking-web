package com.example.webbookingroom.service.impl;

import com.example.webbookingroom.dto.CreateRoomDto;
import com.example.webbookingroom.dto.response.BookerRequest;
import com.example.webbookingroom.dto.response.RoomResponse;
import com.example.webbookingroom.dto.response.ServerResponse;
import com.example.webbookingroom.exception.CustomException;
import com.example.webbookingroom.model.Hotel;
import com.example.webbookingroom.model.Room;
import com.example.webbookingroom.model.User;
import com.example.webbookingroom.repository.HotelRepository;
import com.example.webbookingroom.repository.RoleRepository;
import com.example.webbookingroom.repository.RoomRepository;
import com.example.webbookingroom.repository.UserRepository;
import com.example.webbookingroom.service.RoomService;
import com.example.webbookingroom.util.CommonUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {
    private static final int LIMIT = 5;
    private final HotelRepository hotelRepository;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public ResponseEntity<?> createRoom(CreateRoomDto createRoomDto, Long hotelId) {
        Hotel hotel = hotelRepository.findById(hotelId)
                .orElse(null);
        if (Objects.isNull(hotel)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Không tìm thấy khach san");
        }
        Room room = new Room();
        BeanUtils.copyProperties(createRoomDto, room);
        room.setHotel(hotel);
        roomRepository.save(room);
        return ResponseEntity.ok("Thêm phòng mới thành công");
    }

    @Override
    public ResponseEntity<?> findRooms(String hotelName) {
        Hotel hotel = hotelRepository.findByName(hotelName)
                .orElseThrow(() -> new CustomException("Khách san không tìm thấy"));
        List<Room> roomListByHotel = hotel.getRooms();
        List<RoomResponse> roomResponses = roomListByHotel.stream().map(room -> {
            RoomResponse roomResponse = new RoomResponse();
            BeanUtils.copyProperties(room, roomResponse);
            roomResponse.setId(room.getId().toString());
            roomResponse.setPrice(room.getPrice());
            roomResponse.setCapacity(room.getCapacity().toString());
            return roomResponse;
        }).toList();
        ServerResponse response = CommonUtils.getResponse(HttpStatus.OK, "Get all room successfully", roomResponses);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<?> bookRooms(BookerRequest request) {
        User user = new User();
        BeanUtils.copyProperties(request, user);
        List<Long> roomIds = new ArrayList<>();
        List<String> ids = Arrays.stream(request.getIds().split(",")).toList();
        ids.stream().map(Long::parseLong).forEach(roomIds::add);
        List<Room> rooms = roomIds.stream().map(id -> roomRepository.findById(id)
                .orElseThrow(() -> new CustomException("Khách san không tìm thấy phòng"))).toList();
        user.setUserRoom(rooms);
        user.setUserRole(List.of(Objects.requireNonNull(roleRepository.findByRole("ROLE_USER").orElse(null))));
        userRepository.save(user);
        ServerResponse response = CommonUtils.getResponse(HttpStatus.OK, "Book room successfully", null);
        return ResponseEntity.ok(response);
    }
}
