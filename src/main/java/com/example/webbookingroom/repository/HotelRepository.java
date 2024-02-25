package com.example.webbookingroom.repository;

import com.example.webbookingroom.model.Hotel;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long>, JpaSpecificationExecutor<Hotel> {
    Optional<Hotel> findByName(String name);
    @Override
    void deleteById(@NotNull Long id);
}
