package com.example.webbookingroom.repository;

import com.example.webbookingroom.model.HotelType;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelTypeRepository extends JpaRepository<HotelType, Long> {
}
