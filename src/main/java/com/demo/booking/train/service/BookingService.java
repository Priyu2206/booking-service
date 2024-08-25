package com.demo.booking.train.service;

import com.demo.booking.train.dto.BookingRequest;
import com.demo.booking.train.dto.UpdateBookingRequest;
import com.demo.booking.train.model.Ticket;
import org.springframework.stereotype.Service;

@Service
public interface BookingService {

    Ticket bookTicket(BookingRequest bookingRequest);

    Ticket modifyBooking(String bookingId, UpdateBookingRequest request);

    boolean cancelBooking(String bookingId);

    void removeUserBooking(Long userId);
}