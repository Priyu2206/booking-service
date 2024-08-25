package com.demo.booking.train.controller;

import com.demo.booking.train.model.Seat;
import com.demo.booking.train.enums.Section;
import com.demo.booking.train.service.SeatAllocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/seats")
public class SeatAllocationController {

    private final SeatAllocationService seatAllocationService;

    @Autowired
    public SeatAllocationController(SeatAllocationService seatAllocationService) {
        this.seatAllocationService = seatAllocationService;
    }

    // Endpoint to get all available seats
    @GetMapping
    public ResponseEntity<Map<Section, List<Seat>>> getSeatMap() {
        Map<Section, List<Seat>> availableSeats = seatAllocationService.getSeatMap();
        return ResponseEntity.ok(availableSeats);
    }

    // Endpoint to modify a user's seat
    @PutMapping
    public ResponseEntity<String> modifySeat(@RequestParam String email,
                                             @RequestParam String newSeatNumber,
                                             @RequestParam String newSection) {
        Optional<Seat> ticketOpt = seatAllocationService.modifySeat(email, newSeatNumber, newSection);
        if (ticketOpt.isPresent()) {
            return ResponseEntity.ok("Seat modified successfully.");
        } else {
            return ResponseEntity.badRequest().body("Seat modification failed. Either the seat is unavailable or the user doesn't have an active ticket.");
        }
    }

    // Endpoint to view all seats in a particular section
    @GetMapping("/section/{section}")
    public ResponseEntity<List<Seat>> getSeatsBySection(@PathVariable Section section) {
        List<Seat> seatsInSection = seatAllocationService.getSeatsBySection(section);
        return ResponseEntity.ok(seatsInSection);
    }

    // Endpoint to release a seat (used for cancellation)
    @PatchMapping("/release")
    public ResponseEntity<String> releaseSeat(@RequestParam String seatNumber) {
        seatAllocationService.releaseSeat(seatNumber);
        return ResponseEntity.ok("Seat released successfully.");
    }
}