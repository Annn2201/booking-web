package com.example.webbookingroom.service.impl;

import com.example.webbookingroom.dto.response.ServerResponse;
import com.example.webbookingroom.dto.response.TypeResponse;
import com.example.webbookingroom.model.HotelType;
import com.example.webbookingroom.repository.HotelTypeRepository;
import com.example.webbookingroom.service.TypeService;
import com.example.webbookingroom.util.CommonUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TypeServiceImpl implements TypeService {
    private final HotelTypeRepository hotelTypeRepository;
    @Override
    public ResponseEntity<?> getAllType() {
        List<HotelType> hotelTypeList = hotelTypeRepository.findAll();
        List<TypeResponse> typeResponses = hotelTypeList.stream().map(hotelType -> {
            TypeResponse typeResponse = new TypeResponse();
            typeResponse.setId(hotelType.getId().toString());
            typeResponse.setName(hotelType.getName());
            return typeResponse;
        }).toList();
        ServerResponse response = CommonUtils.getResponse(HttpStatus.OK, "Get all hotel successfully", typeResponses);
        return ResponseEntity.ok(response);
    }
}
