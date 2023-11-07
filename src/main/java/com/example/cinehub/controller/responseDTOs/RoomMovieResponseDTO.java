package com.example.cinehub.controller.responseDTOs;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Builder
@Value
public class RoomMovieResponseDTO {

    @JsonProperty("room_number")
    Integer roomNumber;

    @JsonProperty("room_capacity")
    Integer capacity;

    @JsonProperty("cinema_slug")
    String cinemaSlug;

    @JsonProperty("show_times")
    List<ShowTimeResponseDTO> showTimes;
}
