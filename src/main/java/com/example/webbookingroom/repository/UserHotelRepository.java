package com.example.webbookingroom.repository;

import com.example.webbookingroom.model.UserHotel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface UserHotelRepository extends JpaRepository<UserHotel, Long>, JpaSpecificationExecutor<UserHotel> {
    @Override
    Page<UserHotel> findAll(Specification<UserHotel> spec, Pageable pageable);
}
