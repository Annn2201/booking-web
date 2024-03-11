package com.example.webbookingroom.controller;

import com.example.webbookingroom.dto.ChangePasswordDTO;
import com.example.webbookingroom.dto.UserDTO;
import com.example.webbookingroom.service.AuthenService;
import com.example.webbookingroom.service.UserService;
import com.example.webbookingroom.service.VoucherService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class UserController {
    private final UserService userService;
    private final AuthenService authenService;
    private final VoucherService voucherService;
    @GetMapping("/user")
    public ResponseEntity<?> getDetailUser(HttpServletRequest request) {
        return userService.getUserDetail(request);
    }

    @PostMapping("/user")
    public ResponseEntity<?> updateUser(HttpServletRequest request,
                                        @RequestBody UserDTO userDTO) {
        return userService.updateUserDetail(request, userDTO);
    }

    @PostMapping("/user/change-password")
    public ResponseEntity<?> changePassword(HttpServletRequest request,
                                            @RequestBody ChangePasswordDTO changePasswordDTO) {
        return userService.changePassword(request, changePasswordDTO);
    }

    @GetMapping("user/vouchers")
    public ResponseEntity<?> getVouchers(HttpServletRequest request,
                                         @RequestParam int currentPage,
                                         @RequestParam(required = false) String hotelName) {
        return voucherService.findVouchers(currentPage, hotelName, request);

    }
}
