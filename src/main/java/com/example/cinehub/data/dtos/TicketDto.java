package com.example.cinehub.data.dtos;

import com.example.cinehub.data.entity.Ticket;
import com.example.cinehub.data.entity.TicketCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.UUID;

/**
 * Data Transfer Object (DTO) for the {@link Ticket} entity.
 * <p>
 * This class represents a simplified, serializable version of a Ticket, designed to facilitate
 * the transfer of ticket-related data across different layers of the application, particularly
 * in API communications. It abstracts the internal details of the Ticket entity and offers
 * a client-friendly data model.
 * <p>
 * The class includes validation annotations to ensure data integrity and correctness when
 * receiving data from or sending data to clients. It encapsulates details such as ticket type,
 * price, showtime, reservation status, and the associated seat.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class TicketDto implements Serializable {

    /**
     * Unique identifier for the ticket.
     */
    private UUID id;

    /**
     * The category or type of the ticket. This field is mandatory.
     */
    @NotNull
    private TicketCategory ticketType;

    /**
     * The price of the ticket. This field is mandatory and must be a non-negative value.
     */
    @NotNull(message = "Price can not be null")
    @PositiveOrZero(message = "Price can not be negative")
    private Double price;

    /**
     * The showtime associated with this ticket. This field is mandatory.
     */
    @NotNull
    private ShowTimeDto showTime;

    /**
     * Indicates whether the ticket is reserved. This field is mandatory.
     */
    @NotNull
    private Boolean isReserved;

    /**
     * The seat associated with this ticket. This field is mandatory.
     */
    @NotNull
    private SeatDto seat;
}