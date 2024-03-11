package com.example.webbookingroom.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String number;
    private String username;
    private String password;
    private String confirmPassword;
    private String identifyNumber;
    private String role;
}
