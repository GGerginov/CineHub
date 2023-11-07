package com.example.cinehub.controller.requestDTOs;

import com.example.cinehub.data.anotation.CinemaSlugExist;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;

public record RoomMoviesRequestDTO(
        @CinemaSlugExist
        @JsonProperty("cinema_slug")
        @NotNull(message = "Cinema slug can not be null") String cinemaSlug,
        @JsonProperty("room_number")
        @NotNull(message = "Room number can not be null")
        @Positive(message = "Room number can not be negative or zero") Integer roomNumber,
        @JsonProperty("start_time")
        @NotNull(message = "Start time can not be null")
        @FutureOrPresent(message = "Start time can not be in the past") LocalDateTime start_time,
        @JsonProperty("end_time")
        @NotNull(message = "End time can not be null")
        @FutureOrPresent(message = "End time can not be in the past") LocalDateTime end_time) {

}
