package com.example.webbookingroom.repository;

import com.example.webbookingroom.model.UserRoom;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface UserRoomRepository extends JpaRepository<UserRoom, Long>, JpaSpecificationExecutor<UserRoom> {

}
