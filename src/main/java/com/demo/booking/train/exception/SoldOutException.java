package com.demo.booking.train.exception;

import org.springframework.http.HttpStatus;

public class SoldOutException extends CustomException {
    public SoldOutException() {
        super("Selected seat is sold out, Please select a different seat and proceed with the booking ",
                HttpStatus.CONFLICT);
    }
}
