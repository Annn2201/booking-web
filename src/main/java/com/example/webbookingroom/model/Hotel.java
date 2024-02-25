package com.example.webbookingroom.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Hotel {
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
    @Basic
    @Column(name = "description")
    private String description;
    @Basic
    @Column(name = "image")
    private String image;
    @Basic
    @Column(name = "address")
    private String address;
    @Basic
    @Column(name = "rating")
    private float rating;
    @OneToMany(mappedBy = "hotel")
    private List<Room> rooms;
    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;
    @ManyToMany(mappedBy = "userHotelList")
    private List<User> users;


}
