package com.example.cinehub.data.dtos;

import com.example.cinehub.data.entity.Reservation;

import java.io.Serializable;

/**
 * DTO for {@link Reservation}
 */
public record ReservationDto(String id, SeatDto seat, MovieDto movie, Boolean isReserved) implements Serializable {
}