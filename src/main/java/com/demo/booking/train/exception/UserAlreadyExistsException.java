package com.demo.booking.train.exception;

import org.springframework.http.HttpStatus;

public class UserAlreadyExistsException extends CustomException {
    public UserAlreadyExistsException(String email) {
        super("User with email " + email + " already exists.", HttpStatus.BAD_REQUEST);
    }
}
