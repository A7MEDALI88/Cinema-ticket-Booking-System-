package com.movie.bookingSystem.controller;

import com.movie.bookingSystem.dto.HallRequest;
import com.movie.bookingSystem.model.Hall;
import com.movie.bookingSystem.service.HallService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/halls")
public class AdminHallController {

    private final HallService hallService;

    public AdminHallController(HallService hallService) {
        this.hallService = hallService;
    }

    @PostMapping
    public ResponseEntity<?> addHall(@RequestBody HallRequest request) {
        try {
            Hall hall = hallService.addHall(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(hall);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateHall(@PathVariable Long id, @RequestBody HallRequest request) {
        try {
            Hall hall = hallService.updateHall(id, request);
            return ResponseEntity.ok(hall);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteHall(@PathVariable Long id) {
        try {
            hallService.deleteHall(id);
            return ResponseEntity.ok("تم حذف الصالة بنجاح");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}