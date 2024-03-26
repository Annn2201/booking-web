package com.example.webbookingroom.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Image {
    @Id
    private Long id;
    private String imageUrl;
    @ManyToOne
    @JoinColumn(name = "hotel_id")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private Hotel hotel;
}
