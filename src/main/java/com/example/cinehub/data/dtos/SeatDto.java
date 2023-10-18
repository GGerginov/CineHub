package com.example.cinehub.data.dtos;

import java.io.Serializable;

/**
 * DTO for {@link com.example.cinehub.data.entity.Seat}
 */
public record SeatDto(String id, Integer rowNumber, Integer seatNumber, RoomDto room) implements Serializable {
}