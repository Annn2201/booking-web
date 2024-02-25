package com.example.webbookingroom.service;

import com.example.webbookingroom.dto.LoginDTO;
import com.example.webbookingroom.dto.RegisterDTO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
public interface AuthenService {
    ResponseEntity<?> register(RegisterDTO registerDto);
    ResponseEntity<?> checkValidUser(LoginDTO loginDTO);
    ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response);
}
