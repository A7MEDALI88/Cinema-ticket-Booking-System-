package com.movie.bookingSystem.repository;

import com.movie.bookingSystem.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import jakarta.persistence.LockModeType;
import java.util.List;
import java.util.Optional;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    long countByHallId(Long hallId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT m FROM Movie m WHERE m.id = :id")
    Optional<Movie> findByIdForUpdate(Long id);

    List<Movie> findByHallId(Long hallId);
}