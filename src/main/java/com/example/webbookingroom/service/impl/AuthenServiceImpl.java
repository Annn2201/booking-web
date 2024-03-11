package com.example.webbookingroom.service.impl;

import com.example.webbookingroom.config.JwtUtilities;
import com.example.webbookingroom.dto.LoginDTO;
import com.example.webbookingroom.dto.RegisterDTO;
import com.example.webbookingroom.dto.response.JwtResponse;
import com.example.webbookingroom.exception.CustomException;
import com.example.webbookingroom.model.Role;
import com.example.webbookingroom.model.User;
import com.example.webbookingroom.repository.RoleRepository;
import com.example.webbookingroom.repository.UserRepository;
import com.example.webbookingroom.service.AuthenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthenServiceImpl implements AuthenService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtUtilities jwtUtil;
    private final PasswordEncoder passwordEncoder;
    @Override
    public ResponseEntity<?> register(RegisterDTO registerDto) {
        List<User> listUser = userRepository.findAll();
        List<String> emailIsUsed = listUser.stream().map(User::getEmail).toList();
        if (emailIsUsed.contains(registerDto.getEmail())) {
            return new ResponseEntity<>("Email already exists", HttpStatus.BAD_REQUEST);
        }
        List<Role> userRoles = new ArrayList<>();
        userRoles.add(roleRepository.findByRole(registerDto.getRole().toString()).orElseThrow(() -> new CustomException("Role not found")));
        String encodedPassword = passwordEncoder.encode(registerDto.getPassword());
        User user = new User();
        user.setFirstName(registerDto.getFirstName());
        user.setLastName(registerDto.getLastName());
        user.setEmail(registerDto.getEmail());
        user.setNumber(registerDto.getNumber());
        user.setUsername(registerDto.getUsername());
        user.setPassword(encodedPassword);
        user.setIdentifyNumber(registerDto.getIdentifyNumber());
        user.setUserRole(userRoles);
        userRepository.save(user);
        return new ResponseEntity<>("Register successfully with Email: " + registerDto.getEmail(),HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> checkValidUser(LoginDTO loginDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtil.generateToken(authentication);
        User user = (User) authentication.getPrincipal();
        if (user == null) {
            return new  ResponseEntity<>("Tài khoản hoặc mật khẩu bị sai, hãy nhập lại", HttpStatus.NOT_FOUND);
        }
        List<String> roles = user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        JwtResponse response = JwtResponse.builder()
                .token(jwt)
                .id(user.getUserId())
                .username(user.getUsername())
                .email(user.getEmail())
                .roles(roles)
                .build();
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        response.setHeader("authorization", "");
        return ResponseEntity.ok("Đã đăng xuất thành công");
    }
}
