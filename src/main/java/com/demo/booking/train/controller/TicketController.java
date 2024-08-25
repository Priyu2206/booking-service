package com.demo.booking.train.controller;

import com.demo.booking.train.enums.Section;
import com.demo.booking.train.model.Ticket;
import com.demo.booking.train.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/ticket")
public class TicketController {

    private final TicketService ticketService;

    @Autowired
    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping("{bookingId}")
    public ResponseEntity<Ticket> getTicket(@PathVariable String bookingId) {
        Optional<Ticket> ticketOpt = ticketService.getTicketByBookingId(bookingId);
        return ticketOpt.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/section/{section}")
    public ResponseEntity<List<Ticket>> getAllTicketsBySection(@PathVariable Section section) {
        List<Ticket> tickets = ticketService.getTicketsBySection(section);
        return ResponseEntity.ok(tickets);
    }

}
