package com.example.webbookingroom.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Utility {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Basic
    @Column(name = "name")
    private String name;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "room_utility",
            joinColumns = @JoinColumn(name = "utility_id"),
            inverseJoinColumns = @JoinColumn(name = "room_id"))
    private List<Room> rooms = new ArrayList<>();
}
