package com.demo.booking.train.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateBookingRequest {

    @NotBlank(message = "Section should be valid")
    String section;
    @NotBlank(message = "SeatNumber should be valid")
    String seatNumber;
}
