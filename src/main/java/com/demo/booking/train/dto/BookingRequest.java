package com.demo.booking.train.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BookingRequest {
    @NotBlank(message = "Source should be valid")
    String source;
    @NotBlank(message = "Destination should be valid")
    String destination;
    @NotNull(message = "UserId should be valid")
    Long userId;

}
