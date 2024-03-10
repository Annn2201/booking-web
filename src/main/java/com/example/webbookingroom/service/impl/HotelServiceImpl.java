package com.example.webbookingroom.service.impl;

import com.example.webbookingroom.dto.CoRegisterDTO;
import com.example.webbookingroom.dto.response.HotelResponse;
import com.example.webbookingroom.exception.CustomException;
import com.example.webbookingroom.model.*;
import com.example.webbookingroom.repository.*;
import com.example.webbookingroom.service.HotelService;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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
    public ResponseEntity<?> findSuitableHotel(String location, String hotelType, int numberOfPeople) {
        Specification<Hotel> spec = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (Objects.nonNull(location)) {
                predicates.add(criteriaBuilder.like(root.get("location").get("name"), "%" + location + "%"));
            }
            if (Objects.nonNull(hotelType)) {
                predicates.add(criteriaBuilder.like(root.get("hotelType"), "%" + hotelType + "%"));
            }
            predicates.add(criteriaBuilder.equal(root.get("numberOfPeople"), numberOfPeople));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
        List<Hotel> hotels = hotelRepository.findAll(spec);
        List<Long> hotelIds = new ArrayList<>(hotels.stream().map(Hotel::getId).toList());
        List<UserHotel> userHotels = userHotelRepository.findAllById(hotelIds);
        List<Long> userHotelIds = userHotels.stream().map(UserHotel::getUserId).toList();
        hotelIds.retainAll(userHotelIds);
        List<>

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
    public ResponseEntity<?> deleteHotel(Long id) {
        hotelRepository.deleteById(id);
        return ResponseEntity.ok("Xóa thanh cong");
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
