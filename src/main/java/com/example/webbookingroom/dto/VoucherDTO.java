package com.example.webbookingroom.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VoucherDTO {
    private String name;
    private String hotelName;
    private String expiryDate;
}
