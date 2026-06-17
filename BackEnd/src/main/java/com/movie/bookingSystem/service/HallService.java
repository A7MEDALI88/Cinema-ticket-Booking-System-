package com.movie.bookingSystem.service;

import com.movie.bookingSystem.dto.HallDTO;
import com.movie.bookingSystem.dto.HallRequest;
import com.movie.bookingSystem.model.Hall;
import com.movie.bookingSystem.model.Movie;
import com.movie.bookingSystem.repository.HallRepository;
import com.movie.bookingSystem.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HallService {

    private final HallRepository hallRepository;
    private final MovieRepository movieRepository;

    public HallService(HallRepository hallRepository, MovieRepository movieRepository) {
        this.hallRepository = hallRepository;
        this.movieRepository = movieRepository;
    }

    public List<HallDTO> getAllHalls() {
        return hallRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public Hall addHall(HallRequest request) {
        if (hallRepository.existsByName(request.getName())) {
            throw new RuntimeException("اسم الصالة موجود مسبقاً");
        }
        Hall hall = new Hall();
        hall.setName(request.getName());
        hall.setLocation(request.getLocation());
        return hallRepository.save(hall);
    }

    public Hall updateHall(Long id, HallRequest request) {
        Hall hall = hallRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("الصالة غير موجودة"));
        hall.setName(request.getName());
        hall.setLocation(request.getLocation());
        return hallRepository.save(hall);
    }


    public void deleteHall(Long id) {
        if (!hallRepository.existsById(id)) {
            throw new RuntimeException("الصالة غير موجودة");
        }
        long movieCount = movieRepository.countByHallId(id);
        if (movieCount > 0) {
            throw new RuntimeException("لا يمكن حذف الصالة لأنها تحتوي على " + movieCount + " فيلم/أفلام");
        }
        hallRepository.deleteById(id);
    }

    private HallDTO mapToDto(Hall hall) {
        HallDTO dto = new HallDTO();
        dto.setId(hall.getId());
        dto.setName(hall.getName());
        dto.setLocation(hall.getLocation());
        dto.setMovieCount(movieRepository.countByHallId(hall.getId()));
        return dto;
    }
}