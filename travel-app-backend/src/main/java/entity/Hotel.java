package com.travel.travelapp.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "hotels")
@Data
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long hotelId;

    private String name;
    private String city;
    private String location;
    private double rating;
    private int pricePerNight;
    private String imageUrl;
}
