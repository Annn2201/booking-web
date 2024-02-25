package com.example.webbookingroom.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CoRegisterDTO {
    private String lastName;
    private String type;
    private String location;
    private String address;
    private String username;
    private String password;
    private String email;
    private String number;
    private String role;
}
