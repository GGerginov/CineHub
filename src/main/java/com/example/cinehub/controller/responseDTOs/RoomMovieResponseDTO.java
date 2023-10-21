package com.example.cinehub.controller.responseDTOs;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.util.List;

@Builder
public class RoomMovieResponseDTO {

    @JsonProperty("room_number")
    private Integer roomNumber;

    @JsonProperty("room_capacity")
    private Integer capacity;

    @JsonProperty("cinema_slug")
    private String cinemaSlug;

    @JsonProperty("show_times")
    private List<ShowTimeResponseDTO> showTimes;
}
