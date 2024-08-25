package com.demo.booking.train.service;

import com.demo.booking.train.dto.BookingRequest;
import com.demo.booking.train.model.Seat;
import com.demo.booking.train.enums.Section;
import com.demo.booking.train.model.Ticket;
import com.demo.booking.train.model.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface TicketService {
    Ticket purchaseTicket(BookingRequest request, Seat seat, User user);

    Optional<Ticket> getTicketByBookingId(String bookingId);

    boolean cancelTicket(String bookingId);

    List<Ticket> getTicketsBySection(Section section);

    List<Ticket> getTicketByUserId(Long userId);
}