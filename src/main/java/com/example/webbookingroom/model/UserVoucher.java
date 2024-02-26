package com.example.webbookingroom.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.util.Objects;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_voucher", schema = "public", catalog = "web_booking_room")
public class UserVoucher {
    @Basic
    @Column(name = "voucher_id")
    private long voucherId;
    @Basic
    @Column(name = "user_id")
    private long userId;
    @Basic
    @Column(name = "expire_date")
    private Date expiryDate;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

}
