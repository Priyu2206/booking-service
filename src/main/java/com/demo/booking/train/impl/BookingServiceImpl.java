package com.demo.booking.train.impl;

import com.demo.booking.train.dto.BookingRequest;
import com.demo.booking.train.dto.UpdateBookingRequest;
import com.demo.booking.train.enums.TicketStatus;
import com.demo.booking.train.exception.BookingNotFoundException;
import com.demo.booking.train.exception.CustomException;
import com.demo.booking.train.exception.SoldOutException;
import com.demo.booking.train.exception.UserNotFoundException;
import com.demo.booking.train.model.Seat;
import com.demo.booking.train.model.Ticket;
import com.demo.booking.train.model.User;
import com.demo.booking.train.service.BookingService;
import com.demo.booking.train.service.SeatAllocationService;
import com.demo.booking.train.service.TicketService;
import com.demo.booking.train.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;

@Service
public class BookingServiceImpl implements BookingService {

    private final UserService userService;
    private final TicketService ticketService;
    private final SeatAllocationService seatAllocationService;

    public BookingServiceImpl(UserService userService, TicketService ticketService, SeatAllocationService seatAllocationService) {
        this.userService = userService;
        this.ticketService = ticketService;
        this.seatAllocationService = seatAllocationService;
    }

    @Override
    public Ticket bookTicket(BookingRequest request) {
        Optional<User> user = userService.getUserById(request.getUserId());
        if (user.isEmpty()) {
            throw new UserNotFoundException(String.valueOf(request.getUserId()));
        }
        Optional<Seat> seatOpt = seatAllocationService.allocateSeat();
        if (seatOpt.isPresent()) {
            Ticket ticket = ticketService.purchaseTicket(request, seatOpt.get(), user.get());
            ticket.setSeat(seatOpt.get());
            return ticket;
        }

        throw new SoldOutException();
    }

    @Override
    public Ticket modifyBooking(String bookingId, UpdateBookingRequest request) {
        Optional<Ticket> ticketOpt = ticketService.getTicketByBookingId(bookingId);
        if (ticketOpt.isEmpty()) {
            throw new BookingNotFoundException(bookingId);
        }
        Ticket ticket = ticketOpt.get();
        Optional<Seat> modifiedSeatOpt = seatAllocationService.modifySeat(ticket.getSeat().getSeatNumber(),
                request.getSeatNumber(), request.getSection());
        if (modifiedSeatOpt.isEmpty()) {
            throw new SoldOutException();
        }
        ticket.setSeat(modifiedSeatOpt.get());
        return ticket;
    }

    @Override
    public boolean cancelBooking(String bookingId) {
        Optional<Ticket> ticketOpt = ticketService.getTicketByBookingId(bookingId);
        if (ticketOpt.isPresent()) {
            seatAllocationService.releaseSeat(ticketOpt.get().getSeat().getSeatNumber());
            ticketService.cancelTicket(bookingId);
            return true;
        }
        return false;
    }

    @Override
    public void removeUserBooking(Long userId) {
        List<Ticket> tickets = ticketService.getTicketByUserId(userId);
        if (CollectionUtils.isEmpty(tickets)) {
            throw new CustomException("No active bookings for the user" + userId, HttpStatus.BAD_REQUEST);
        }
        tickets.forEach(ticket -> {
            // Release the allocated seat
            seatAllocationService.releaseSeat(ticket.getSeat().getSeatNumber());
            // Update the ticket status
            ticket.setStatus(TicketStatus.CANCELLED);
        });
    }
}


