package com.example.webbookingroom.controller;

import com.example.webbookingroom.config.JwtUtilities;
import com.example.webbookingroom.dto.CoRegisterDTO;
import com.example.webbookingroom.dto.LoginDTO;
import com.example.webbookingroom.dto.RegisterDTO;
import com.example.webbookingroom.service.AuthenService;
import com.example.webbookingroom.service.HotelService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@CrossOrigin(origins="*")
public class AuthenController {
    private final AuthenService authenService;
    private final HotelService hotelService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterDTO registerDto) {
        return authenService.register(registerDto);
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginDTO loginDTO) {
        return authenService.checkValidUser(loginDTO);
    }
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
        return authenService.logout(request, response);
    }

    @PostMapping("/co-register")
    public ResponseEntity<?> coRegister(@RequestBody CoRegisterDTO coRegisterDTO) {
        return hotelService.createHotel(coRegisterDTO);
    }
}
