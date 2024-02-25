package com.example.webbookingroom.controller;

import com.example.webbookingroom.dto.ChangePasswordDTO;
import com.example.webbookingroom.dto.UserDTO;
import com.example.webbookingroom.service.AuthenService;
import com.example.webbookingroom.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class UserController {
    private final UserService userService;
    private final AuthenService authenService;
    @GetMapping("/user")
    public ResponseEntity<?> getDetailUser(HttpServletRequest request) {
        return userService.getUserDetail(request);
    }

    @PostMapping("/user")
    public ResponseEntity<?> updateUser(HttpServletRequest request, UserDTO userDTO) {
        return userService.updateUserDetail(request, userDTO);
    }

    @PostMapping("/user/change-password")
    public ResponseEntity<?> changePassword(HttpServletRequest request, ChangePasswordDTO changePasswordDTO) {
        return userService.changePassword(request, changePasswordDTO);
    }
}
