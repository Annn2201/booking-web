package com.example.webbookingroom.repository;

import com.example.webbookingroom.model.Room;
import com.example.webbookingroom.model.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    @NotNull
    List<User> findAll();

    List<User> findByUserRoom(List<Room> userRoom);
}
