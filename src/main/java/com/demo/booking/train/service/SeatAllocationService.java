package com.demo.booking.train.service;

import com.demo.booking.train.model.Seat;
import com.demo.booking.train.enums.Section;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface SeatAllocationService {
    Optional<Seat> allocateSeat();

    Optional<Seat> modifySeat(String currentSeatNumber, String newSeatNumber, String newSection);

    boolean isSoldOut();

    void releaseSeat(String seatNumber);

    List<Seat> getSeatsBySection(Section section);

    Map<Section, List<Seat>> getSeatMap();
}
