package com.example.cinehub.data.dtos;

import com.example.cinehub.data.entity.TicketCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.io.Serializable;
import java.util.UUID;

/**
 * DTO for {@link com.example.cinehub.data.entity.Ticket}
 */

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class TicketDto implements Serializable {

    private UUID id;
    @NotNull
    private TicketCategory ticketType;
    @NotNull(message = "Price can not be null")
    @PositiveOrZero(message = "Price can not be negative")
    private Double price;
    @NotNull
    private ShowTimeDto showTime;
    @NotNull
    private Boolean isReserved;
    @NotNull
    private SeatDto seat;
}