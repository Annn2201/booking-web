package com.example.webbookingroom.repository;

import com.example.webbookingroom.model.UserVoucher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserVoucherRepository extends JpaRepository<UserVoucher, Long> {
    List<UserVoucher> findByUserId(Long userId);
}
