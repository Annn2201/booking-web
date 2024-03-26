package com.example.webbookingroom.service.impl;

import com.example.webbookingroom.config.JwtUtilities;
import com.example.webbookingroom.dto.ChangePasswordDTO;
import com.example.webbookingroom.dto.UserDTO;
import com.example.webbookingroom.dto.response.CustomerResponse;
import com.example.webbookingroom.dto.response.ServerResponse;
import com.example.webbookingroom.exception.CustomException;
import com.example.webbookingroom.model.User;
import com.example.webbookingroom.repository.UserRepository;
import com.example.webbookingroom.service.UserService;
import com.example.webbookingroom.util.CommonUtils;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtilities jwtUtil;

    @Override
    public ResponseEntity<?> getUserDetail(HttpServletRequest request) {
        User user = userRepository.findByUsername(jwtUtil.getUsername(request)).orElseThrow(() -> new CustomException("User not found"));
        if (Objects.isNull(user)) {
            return new ResponseEntity<>("Không tìm thấy người dùng", HttpStatus.BAD_REQUEST);
        }
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(user, userDTO);
        return ResponseEntity.ok(userDTO);
    }

    @Override
    public ResponseEntity<?> updateUserDetail(HttpServletRequest request, UserDTO userDTO) {
        User userUpdated = userRepository.findByUsername(jwtUtil.getUsername(request)).orElseThrow(() -> new CustomException("User not found"));
        BeanUtils.copyProperties(userDTO, userUpdated);
        userRepository.save(userUpdated);
        ServerResponse serverResponse = CommonUtils.getResponse(HttpStatus.OK, "Update user successfully", userUpdated);
        return new ResponseEntity<>(serverResponse, HttpStatus.OK);
    }



    @Override
    public ResponseEntity<?> changePassword(HttpServletRequest request, ChangePasswordDTO changePasswordDTO) {
        User userUpdated = userRepository.findByUsername(jwtUtil.getUsername(request)).orElseThrow(() -> new CustomException("User not found"));
        if (!passwordEncoder.matches(changePasswordDTO.getOldPassword(), userUpdated.getPassword())) {
            throw new CustomException("Old password is not correct");
        }
        userUpdated.setPassword(passwordEncoder.encode(changePasswordDTO.getNewPassword()));
        return ResponseEntity.ok("Change password successfully");
    }

    @Override
    public ResponseEntity<?> getAllCustomer() {
        List<User> user = userRepository.findAll();
        List<User> customer = user.stream().filter(user1 -> user1.getUserRole().stream().anyMatch(role -> role.getRole().equals("ROLE_USER"))).toList();
        List<CustomerResponse> customerResponseList = customer.stream().map(user1 -> {
            CustomerResponse customerResponse = new CustomerResponse();
            customerResponse.setId(user1.getUserId());
            customerResponse.setFirstName(user1.getFirstName());
            customerResponse.setLastName(user1.getLastName());
            customerResponse.setEmail(user1.getEmail());
            customerResponse.setNumber(user1.getNumber());
            String hotelName = user1.getUserRoom().get(0).getHotel().getName();
            if (StringUtils.isBlank(hotelName)) {
                customerResponse.setHotel("Đang không nghỉ dưỡng ở khách sạn nào");
            } else {
                customerResponse.setHotel(hotelName);
            }
            return customerResponse;
        }).toList();
        ServerResponse serverResponse = CommonUtils.getResponse(HttpStatus.OK, "Get all customer successfully", customerResponseList);
        return ResponseEntity.ok(serverResponse);
    }

    @Override
    public ResponseEntity<?> deleteCustomer(Long id) {
        userRepository.deleteById(id);
        ServerResponse serverResponse = CommonUtils.getResponse(HttpStatus.OK, "Delete customer successfully", null);
        return new ResponseEntity<>(serverResponse, HttpStatus.OK);
    }


}
