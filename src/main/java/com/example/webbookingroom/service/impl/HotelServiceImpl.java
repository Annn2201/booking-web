package com.example.webbookingroom.service.impl;

import com.example.webbookingroom.dto.CoRegisterDTO;
import com.example.webbookingroom.dto.response.HotelResponse;
import com.example.webbookingroom.exception.CustomException;
import com.example.webbookingroom.model.Hotel;
import com.example.webbookingroom.model.Location;
import com.example.webbookingroom.model.Role;
import com.example.webbookingroom.model.User;
import com.example.webbookingroom.repository.HotelRepository;
import com.example.webbookingroom.repository.LocationRepository;
import com.example.webbookingroom.repository.RoleRepository;
import com.example.webbookingroom.repository.UserRepository;
import com.example.webbookingroom.service.HotelService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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
    public ResponseEntity<?> addHotelToUser(CoRegisterDTO coRegisterDTO, Long userId) {
        Hotel hotel = new Hotel();
        BeanUtils.copyProperties(coRegisterDTO, hotel);
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
