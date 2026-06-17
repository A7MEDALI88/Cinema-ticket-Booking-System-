package com.movie.bookingSystem.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ShowtimeDTO {
    private Long id;
    private Long movieId;
    private Long hallId;
    private LocalDateTime showTime;
    private BigDecimal price;
    private Integer availableSeats;
    // للعرض فقط
    private String movieTitle;
    private String hallName;
    private String hallLocation;
}