package com.demo.booking.train.controller;

import com.demo.booking.train.dto.BookingRequest;
import com.demo.booking.train.dto.UpdateBookingRequest;
import com.demo.booking.train.model.Ticket;
import com.demo.booking.train.service.BookingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/book")
public class BookingController {

    private final BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    // Endpoint to create booking
    @PostMapping
    public ResponseEntity<Ticket> book(@RequestBody @Valid BookingRequest request) {
        Ticket ticket = bookingService.bookTicket(request);
        return ResponseEntity.ok(ticket);
    }

    // Endpoint to update booking
    @PutMapping("{bookingId}")
    public ResponseEntity<Ticket> update(@PathVariable String bookingId,
                                         @RequestBody @Valid UpdateBookingRequest request) {
        Ticket ticket = bookingService.modifyBooking(bookingId, request);
        return ResponseEntity.ok(ticket);
    }

    // Endpoint to cancel a ticket
    @DeleteMapping("{bookingId}")
    public ResponseEntity<String> cancel(@PathVariable String bookingId) {
        boolean cancelled = bookingService.cancelBooking(bookingId);
        if (cancelled) {
            return ResponseEntity.ok("Ticket cancelled successfully.");
        } else {
            return ResponseEntity.badRequest().body("Cancellation failed. Ticket might not exist or is already cancelled.");
        }
    }

    @DeleteMapping("/remove/user/{userId}")
    public ResponseEntity<String> removeUser(@PathVariable Long userId) {
        bookingService.removeUserBooking(userId);
        return ResponseEntity.ok("User removed successfully from the train.");
    }
}

