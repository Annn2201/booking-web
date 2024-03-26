package com.example.webbookingroom.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomResponse {
    private String id;
    private String name;
    private String type;
    private String image;
    private String description;
    private String capacity;
    private Long price;
}
