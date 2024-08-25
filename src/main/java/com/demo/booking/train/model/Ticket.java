package com.demo.booking.train.model;

import com.demo.booking.train.enums.TicketStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Ticket {
    private String bookingId;
    private String source;
    private String destination;
    private User user;
    private Seat seat;
    private TicketStatus status;

    // Custom getter to conditionally include seat status
    public Seat getSeat() {
        return this.status == TicketStatus.CANCELLED ? null : seat;
    }
}

