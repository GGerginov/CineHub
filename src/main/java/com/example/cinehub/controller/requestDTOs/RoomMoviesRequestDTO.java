package com.example.cinehub.controller.requestDTOs;

import com.example.cinehub.data.anotation.CinemaSlugPresent;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;

@Getter
public class RoomMoviesRequestDTO {

    @CinemaSlugPresent
    @JsonProperty("cinema_slug")
    private String cinemaSlug;

    @JsonProperty("room_number")
    @NotNull
    @Positive
    private Integer roomNumber;

    @NotNull
    @JsonProperty("start_time")
    private LocalDateTime start_time;

    @NotNull
    @JsonProperty("end_time")
    private LocalDateTime end_time;
}
