package com.example.cinehub.controller.requestDTOs;

import com.example.cinehub.data.anotation.CinemaSlugExist;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

@Getter
public class RoomMoviesRequestDTO {

    @CinemaSlugExist
    @NotNull(message = "Cinema slug can not be null")
    @JsonProperty("cinema_slug")
    private String cinemaSlug;

    @JsonProperty("room_number")
    @NotNull(message = "Room number can not be null")
    @Positive(message = "Room number can not be negative or zero")
    private Integer roomNumber;

    @NotNull(message = "Start time can not be null")
    @FutureOrPresent(message = "Start time can not be in the past")
    @JsonProperty("start_time")
    private LocalDateTime start_time;

    @NotNull(message = "End time can not be null")
    @FutureOrPresent(message = "End time can not be in the past")
    @JsonProperty("end_time")
    private LocalDateTime end_time;
}
