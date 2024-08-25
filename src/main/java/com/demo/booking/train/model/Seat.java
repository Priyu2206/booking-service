package com.demo.booking.train.model;

import com.demo.booking.train.enums.SeatStatus;
import com.demo.booking.train.enums.Section;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Seat {
    private Section section;
    private String seatNumber;
    private SeatStatus status;
    private double price;
}
