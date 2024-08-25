package com.demo.booking.train.impl;

import com.demo.booking.train.enums.SeatStatus;
import com.demo.booking.train.model.Seat;
import com.demo.booking.train.enums.Section;
import com.demo.booking.train.service.SeatAllocationService;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class SeatAllocationServiceImpl implements SeatAllocationService {

    private static final int SEATS_PER_SECTION = 50; // Number of seats per section
    private final LinkedHashMap<String, Seat> seats = new LinkedHashMap<>();
    private final Map<Section, Double> sectionPrices = new HashMap<>();
    private int bookedSeats = 0;

    public SeatAllocationServiceImpl() {
        initializeSeats();
    }

    private void initializeSeats() {
        // Calculate the maximum seats based on sections
        int maxSeats = Section.getSectionCount() * SEATS_PER_SECTION;

        // Initialize prices for each section
        sectionPrices.put(Section.A, 500.0);
        sectionPrices.put(Section.B, 750.0);

        // Initialize seats dynamically based on sections
        for (Section section : Section.values()) {
            for (int i = 1; i <= SEATS_PER_SECTION; i++) {
                String seatNumber = section.name() + i;
                seats.put(seatNumber, new Seat(section, seatNumber, SeatStatus.AVAILABLE,
                        sectionPrices.get(section)));
            }
        }
    }

    @Override
    public Optional<Seat> allocateSeat() {
        if (isSoldOut()) {
            return Optional.empty();
        }

        // Allocate the first available seat
        for (Seat seat : seats.values()) {
            if (seat.getStatus() == SeatStatus.AVAILABLE) {
                seat.setStatus(SeatStatus.BOOKED);
                bookedSeats++;
                return Optional.of(seat);
            }
        }

        return Optional.empty();
    }

    @Override
    public Optional<Seat> modifySeat(String currentSeatNumber, String newSeatNumber, String newSection) {
        Seat currentSeat = seats.get(currentSeatNumber);
        Seat newSeat = seats.get(newSeatNumber);

        if (currentSeat != null && currentSeat.getStatus() == SeatStatus.BOOKED &&
                newSeat != null && newSeat.getStatus() == SeatStatus.AVAILABLE) {
            currentSeat.setStatus(SeatStatus.AVAILABLE);
            newSeat.setStatus(SeatStatus.BOOKED);
            return Optional.of(newSeat);
        }

        return Optional.empty();
    }

    @Override
    public boolean isSoldOut() {
        int maxSeats = Section.getSectionCount() * SEATS_PER_SECTION;
        return bookedSeats >= maxSeats;
    }

    @Override
    public void releaseSeat(String seatNumber) {
        Seat seat = seats.get(seatNumber);
        if (seat != null && SeatStatus.BOOKED == seat.getStatus()) {
            seat.setStatus(SeatStatus.AVAILABLE);
            bookedSeats--;
        }
    }


    @Override
    public List<Seat> getSeatsBySection(Section section) {
        return seats.values().stream()
                .filter(seat -> seat.getSection() == section)
                .collect(Collectors.toList());
    }

    @Override
    public Map<Section, List<Seat>> getSeatMap() {
        return seats.values().stream()
                .collect(Collectors.groupingBy(Seat::getSection));
    }
}
