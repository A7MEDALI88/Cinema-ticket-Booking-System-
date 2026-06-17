package com.movie.bookingSystem.dto;

import lombok.Data;

@Data
public class HallDTO {
    private Long id;
    private String name;
    private String location;
    private long movieCount;
}