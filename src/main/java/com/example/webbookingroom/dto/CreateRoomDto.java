package com.example.webbookingroom.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateRoomDto {
    private String name;
    private String type;
    private String description;
    private float price;
    private int capacity;
}
