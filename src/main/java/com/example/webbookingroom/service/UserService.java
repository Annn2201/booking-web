package com.example.webbookingroom.service;

import com.example.webbookingroom.dto.ChangePasswordDTO;
import com.example.webbookingroom.dto.UserDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<?> getUserDetail(HttpServletRequest request);
    ResponseEntity<?> updateUserDetail(HttpServletRequest request, UserDTO userDTO);
    ResponseEntity<?> changePassword(HttpServletRequest request, ChangePasswordDTO changePasswordDTO);
}
