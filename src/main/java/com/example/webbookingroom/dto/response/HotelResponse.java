package com.example.webbookingroom.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotelResponse {
    private String id;
    private String name;
    private String type;
    private String image;
    private String address;
    private String rating;
    private String description;
    private String minPrice;
    private String maxPrice;
}