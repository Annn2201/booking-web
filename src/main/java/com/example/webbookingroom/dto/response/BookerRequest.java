package com.example.webbookingroom.dto.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookerRequest {
    private String ids;
    private String firstName;
    private String lastName;
    private String number;
    private String email;
}
