package com.example.webbookingroom.repository;

import com.example.webbookingroom.model.Hotel;
import com.example.webbookingroom.model.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long>, JpaSpecificationExecutor<Room> {
    @Override
    Page<Room> findAll(Specification<Room> specification, Pageable pageable);
    List<Room> findAllByHotel (Hotel hotel);
}
