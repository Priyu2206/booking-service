package com.demo.booking.train.exception;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends CustomException {
    public UserNotFoundException(String email) {
        super("User Not Found " + email, HttpStatus.BAD_REQUEST);
    }
}
