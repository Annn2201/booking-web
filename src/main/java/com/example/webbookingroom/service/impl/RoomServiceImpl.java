package com.example.webbookingroom.service.impl;

import com.example.webbookingroom.dto.CreateRoomDto;
import com.example.webbookingroom.exception.CustomException;
import com.example.webbookingroom.model.Hotel;
import com.example.webbookingroom.model.Room;
import com.example.webbookingroom.repository.HotelRepository;
import com.example.webbookingroom.repository.RoomRepository;
import com.example.webbookingroom.service.RoomService;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.criteria.Predicate;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {
    private final HotelRepository hotelRepository;
    private final RoomRepository roomRepository;
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
    public ResponseEntity<?> findRooms(int currentPage, Long hotelId, LocalDate checkIn, LocalDate checkOut, HttpServletRequest request) {
        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new CustomException("Khách san không tìm thấy"));
        List<Room> roomListByHotel = roomRepository.findAllByHotel(hotel);
        Specification<Room> spec = ((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if ()
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });
        Page<Room> rooms = roomRepository.findAll(spec, PageUtil.createPageable(currentPage, LIMIT, SORT));
        String username = jwtUtilities.getUsername(request);
        User user = userRepository.findByUsername(username).orElseThrow(() -> new CustomException("User not found"));
        List<UserRoom> userRooms = userRoomRepository.findByUserId(user.getId());
        List<Long> userRoomIds = getUserRoomIds(userRooms);
        List<Room> result = getRoomsAvailable(rooms, userRoomIds);
        if (result.isEmpty()) {
            return ResponseEntity.ok("Không có  được nào khả dụng");
        }
        List<RoomDTO> resultDTO = mapResultToDTO(userRooms, result);
        return ResponseEntity.ok(resultDTO);
    }
}
