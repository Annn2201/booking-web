package com.example.webbookingroom.service.impl;

import com.example.webbookingroom.dto.CoRegisterDTO;
import com.example.webbookingroom.dto.response.HotelResponse;
import com.example.webbookingroom.dto.response.RoomResponse;
import com.example.webbookingroom.dto.response.ServerResponse;
import com.example.webbookingroom.exception.CustomException;
import com.example.webbookingroom.model.*;
import com.example.webbookingroom.repository.*;
import com.example.webbookingroom.service.HotelService;
import com.example.webbookingroom.util.CommonUtils;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService {
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final LocationRepository locationRepository;
    private final HotelRepository hotelRepository;
    private final UserHotelRepository userHotelRepository;

    @Override
    public ResponseEntity<?> getAllHotel() {
        List<Hotel> hotels = hotelRepository.findAll();
        List<HotelResponse> hotelResponses = new ArrayList<>();
        hotels.forEach(hotel -> {
            HotelResponse hotelResponse = new HotelResponse();
            BeanUtils.copyProperties(hotel, hotelResponse);
            List<Image> images = hotel.getImages();
            List<String> imageUrls = images.stream().map(Image::getImageUrl).toList();
            List<Room> rooms = hotel.getRooms();
            rooms.sort(Comparator.comparing(Room::getPrice));
            hotelResponse.setMinPrice(rooms.get(0).getPrice().toString());
            hotelResponse.setMaxPrice(rooms.get(rooms.size() - 1).getPrice().toString());
            hotelResponse.setId(hotel.getId().toString());
            hotelResponse.setImage(imageUrls.get(0));
            hotelResponses.add(hotelResponse);
        });
        ServerResponse serverResponse = CommonUtils.getResponse(HttpStatus.OK, "Get all hotel successfully", hotelResponses);
        return ResponseEntity.ok(serverResponse);
    }

    @Override
    public ResponseEntity<?> findSuitableHotel(String location, String hotelType, LocalDate checkIn, LocalDate checkOut) {
        Specification<Hotel> spec = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (Objects.nonNull(location)) {
                predicates.add(criteriaBuilder.like(root.get("location").get("name"), "%" + location + "%"));
            }
            if (Objects.nonNull(hotelType)) {
                predicates.add(criteriaBuilder.like(root.get("hotelType"), "%" + hotelType + "%"));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
        List<Hotel> hotels = hotelRepository.findAll(spec);
        List<Long> hotelIds = new ArrayList<>(hotels.stream().map(Hotel::getId).toList());
        List<UserHotel> userHotels = userHotelRepository.findAllById(hotelIds);
        List<Long> userHotelIds = userHotels.stream().map(UserHotel::getUserId).toList();
        hotelIds.retainAll(userHotelIds);
        return null;
    }

    @Override
    public ResponseEntity<?> createHotel(CoRegisterDTO coRegisterDTO) {
        User existingHotel = userRepository.findByUsername(coRegisterDTO.getUsername())
                .orElseThrow(() -> new CustomException("Không tìm thấy người dùng này"));
        if (Objects.nonNull(existingHotel)) {
            return ResponseEntity.badRequest().body("Người dùng đã tồn tại !");
        }
        Location location = locationRepository.findByName(coRegisterDTO.getLocation())
                .orElseThrow(() -> new CustomException("Không tìm thấy vùng"));
        List<Role> roles = new ArrayList<>();
        roles.add(roleRepository.findByRole(coRegisterDTO
                        .getRole())
                .orElseThrow(() -> new CustomException("Không tìm thấy Role")));
        Hotel hotel = new Hotel();
        BeanUtils.copyProperties(coRegisterDTO, hotel);
        hotel.setLocation(location);
        hotelRepository.save(hotel);
        User user = new User();
        BeanUtils.copyProperties(coRegisterDTO, user);
        user.setUserRole(roles);
        user.setPassword(passwordEncoder.encode(coRegisterDTO.getPassword()));
        return ResponseEntity.ok(userRepository.save(user));
    }

    @Override
    public ResponseEntity<?> findHotelByHotelName(String name) {
        Hotel hotel = hotelRepository.findByName(name)
                .orElseThrow(() -> new CustomException("Không tìm thấy khách san"));
        HotelResponse hotelResponse = new HotelResponse();
        BeanUtils.copyProperties(hotel, hotelResponse);
        List<Image> images = hotel.getImages();
        List<String> imageUrls = images.stream().map(Image::getImageUrl).toList();
        hotelResponse.setImage(imageUrls.get(0));
        ServerResponse serverResponse = CommonUtils.getResponse(HttpStatus.OK, "Get hotel detail successfully", hotelResponse);
        return ResponseEntity.ok(serverResponse);
    }

    @Override
    public ResponseEntity<?> deleteHotel(String id) {
        hotelRepository.deleteById(Long.parseLong(id));
        ServerResponse serverResponse = CommonUtils.getResponse(HttpStatus.OK, "Xóa khách sạn thành công", null);
        return ResponseEntity.ok(serverResponse);
    }

    @Override
    public ResponseEntity<?> addHotelToCoUser(Long userId, Long hotelId) {
        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new CustomException("Không tìm thấy khach san"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException("Không tìm thấy người dùng"));
        if (Objects.isNull(user)) {
            return ResponseEntity.badRequest().body("Không tìm thấy người dùng");
        }
        List<Hotel> hotels = user.getUserHotelList();
        hotels.add(hotel);
        user.setUserHotelList(hotels);
        userRepository.save(user);
        return new ResponseEntity<>("Thêm khách sạn thành công", HttpStatus.OK);
    }


}
