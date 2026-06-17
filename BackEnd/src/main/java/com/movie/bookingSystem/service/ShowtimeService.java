package com.movie.bookingSystem.service;

import com.movie.bookingSystem.dto.ShowtimeDTO;
import com.movie.bookingSystem.model.Hall;
import com.movie.bookingSystem.model.Movie;
import com.movie.bookingSystem.model.Showtime;
import com.movie.bookingSystem.repository.HallRepository;
import com.movie.bookingSystem.repository.MovieRepository;
import com.movie.bookingSystem.repository.ShowtimeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShowtimeService {

    private final ShowtimeRepository showtimeRepository;
    private final MovieRepository movieRepository;
    private final HallRepository hallRepository;

    public ShowtimeService(ShowtimeRepository showtimeRepository,
                           MovieRepository movieRepository,
                           HallRepository hallRepository) {
        this.showtimeRepository = showtimeRepository;
        this.movieRepository = movieRepository;
        this.hallRepository = hallRepository;
    }

    public List<ShowtimeDTO> getShowtimesByMovie(Long movieId) {
        return showtimeRepository.findByMovieId(movieId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<ShowtimeDTO> getAllShowtimes() {
        return showtimeRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public Showtime getShowtimeById(Long id) {
        return showtimeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Showtime not found with id: " + id));
    }

//    public ShowtimeDTO addShowtime(ShowtimeDTO dto) {
//        Movie movie = movieRepository.findById(dto.getMovieId())
//                .orElseThrow(() -> new RuntimeException("Movie not found"));
//
//        Hall hall = hallRepository.findById(dto.getHallId())
//                .orElseThrow(() -> new RuntimeException("Hall not found"));
//
//        Showtime showtime = new Showtime();
//        showtime.setMovie(movie);
//        showtime.setHall(hall);
//        showtime.setShowTime(dto.getShowTime());
//        showtime.setPrice(dto.getPrice());
//        showtime.setAvailableSeats(hall.getTotalSeats()); // المقاعد المتاحة = مقاعد الصالة
//
//        return mapToDTO(showtimeRepository.save(showtime));
//    }

    public ShowtimeDTO updateShowtime(Long id, ShowtimeDTO dto) {
        Showtime showtime = getShowtimeById(id);

        if (dto.getMovieId() != null) {
            Movie movie = movieRepository.findById(dto.getMovieId())
                    .orElseThrow(() -> new RuntimeException("Movie not found"));
            showtime.setMovie(movie);
        }

        if (dto.getHallId() != null) {
            Hall hall = hallRepository.findById(dto.getHallId())
                    .orElseThrow(() -> new RuntimeException("Hall not found"));
            showtime.setHall(hall);
        }

        if (dto.getShowTime() != null) showtime.setShowTime(dto.getShowTime());
        if (dto.getPrice() != null) showtime.setPrice(dto.getPrice());

        return mapToDTO(showtimeRepository.save(showtime));
    }

    public void deleteShowtime(Long id) {
        if (!showtimeRepository.existsById(id)) {
            throw new RuntimeException("Showtime not found with id: " + id);
        }
        showtimeRepository.deleteById(id);
    }

    private ShowtimeDTO mapToDTO(Showtime s) {
        ShowtimeDTO dto = new ShowtimeDTO();
        dto.setId(s.getId());
        dto.setMovieId(s.getMovie().getId());
        dto.setHallId(s.getHall().getId());
        dto.setShowTime(s.getShowTime());
        dto.setPrice(s.getPrice());
        dto.setAvailableSeats(s.getAvailableSeats());
        dto.setMovieTitle(s.getMovie().getTitle());
        dto.setHallName(s.getHall().getName());
        dto.setHallLocation(s.getHall().getLocation());
        return dto;
    }
}