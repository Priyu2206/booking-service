package com.demo.booking.train.exception;

import org.springframework.http.HttpStatus;

public class BookingNotFoundException extends CustomException {
    public BookingNotFoundException(String bookingId) {
        super("Booking Not Found " + bookingId, HttpStatus.BAD_REQUEST);
    }
}