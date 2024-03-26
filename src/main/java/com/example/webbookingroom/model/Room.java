package com.example.webbookingroom.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Room {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Long id;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "type")
    private String type;
    @ManyToOne
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;
    @Basic
    @Column(name = "description")
    private String description;
    @Basic
    @Column(name = "image")
    private String image;
    @Basic
    @Column(name = "price")
    private Long price;
    @Basic
    @Column(name = "status")
    private String status;
    @Basic
    @Column(name = "capacity")
    private Integer capacity;
    @ManyToMany(mappedBy = "rooms")
    private List<Utility> utilities;
    @ManyToMany(mappedBy = "userRoom")
    private List<User> users;
}
