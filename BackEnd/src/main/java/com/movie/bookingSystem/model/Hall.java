package com.movie.bookingSystem.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "halls")
@Data
public class Hall {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String name;

    @Column(nullable = false)
    private String location;
}