package com.demo.booking.train.impl;

import com.demo.booking.train.constants.BookingConstants;
import com.demo.booking.train.dto.BookingRequest;
import com.demo.booking.train.model.Seat;
import com.demo.booking.train.enums.Section;
import com.demo.booking.train.model.Ticket;
import com.demo.booking.train.enums.TicketStatus;
import com.demo.booking.train.model.User;
import com.demo.booking.train.service.TicketService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class TicketServiceImpl implements TicketService {

    private final List<Ticket> tickets = new ArrayList<>();
    private static final AtomicLong counter = new AtomicLong(1);

    @Override
    public Ticket purchaseTicket(BookingRequest request, Seat seat, User user) {
        Ticket ticket = Ticket.builder().bookingId(generateBookingId()).source(request.getSource()).
                destination(request.getDestination()).seat(seat).status(TicketStatus.BOOKED).
                user(user).build();
        tickets.add(ticket);
        return ticket;
    }

    @Override
    public Optional<Ticket> getTicketByBookingId(String bookingId) {
        return tickets.stream()
                .filter(ticket -> ticket.getBookingId().equals(bookingId))
                .findFirst();
    }


    @Override
    public boolean cancelTicket(String bookingId) {
        Optional<Ticket> ticketOpt = getTicketByBookingId(bookingId);
        if (ticketOpt.isPresent()) {
            Ticket ticket = ticketOpt.get();
            if (TicketStatus.CANCELLED.equals(ticket.getStatus())) {
                return false; // Ticket is already cancelled
            }
            ticket.setStatus(TicketStatus.CANCELLED);
            return true;
        }
        return false;
    }

    @Override
    public List<Ticket> getTicketsBySection(Section section) {
        List<Ticket> result = new ArrayList<>();
        for (Ticket ticket : tickets) {
            if (ticket.getSeat() != null && ticket.getSeat().getSection() == section) {
                result.add(ticket);
            }
        }
        return result;
    }

    @Override
    public List<Ticket> getTicketByUserId(Long userId) {
        return tickets.stream()
                .filter(ticket -> ticket.getUser().getId().equals(userId) &&
                        ticket.getStatus() == TicketStatus.BOOKED).collect(Collectors.toList());
    }

    private static String generateBookingId() {
        long id = counter.getAndIncrement(); // Increment counter for unique ID
        String timestamp = BookingConstants.dateFormat.format(new Date());
        // Combine PREFIX, timestamp, and counter to form a unique booking ID
        return BookingConstants.prefix + "-" + timestamp + "-" + id;
    }

}