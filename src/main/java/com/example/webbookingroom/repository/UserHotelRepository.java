package com.example.webbookingroom.repository;

import com.example.webbookingroom.model.UserHotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserHotelRepository extends JpaRepository<UserHotel, Long> {

}
