package com.movie.bookingSystem.repository;

import com.movie.bookingSystem.model.Hall;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HallRepository extends JpaRepository<Hall, Long> {
    boolean existsByName(String name);
}