package com.example.cinehub.controller.responseDTOs;

import lombok.Builder;
import lombok.Value;
import org.hibernate.validator.constraints.Range;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link com.example.cinehub.data.dtos.SeatDto}
 */
@Value
@Builder
public class SeatResponseDto implements Serializable {
    @Range(message = "Rows can not be more than 20", max = 20)
    Integer rowNumber;
    @Range(message = "Seat number can not be more than 100", max = 100)
    Integer seatNumber;
    Boolean isReserved;
    Integer roomRoomNumber;
    String roomCinemaName;
    LocalDateTime showTimeStartTime;
    LocalDateTime showTimeEndTime;
    String showTimeMovieTitle;
}