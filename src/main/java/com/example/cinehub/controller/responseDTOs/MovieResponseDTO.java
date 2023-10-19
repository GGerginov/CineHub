package com.example.cinehub.controller.responseDTOs;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public class MovieResponseDTO {

    @JsonProperty("movie_description")
    private String description;

    @JsonProperty("movie_start_time")
    private LocalDateTime startTime;

    @JsonProperty("movie_title")
    private String title;

    @JsonProperty("movie_room")
    private RoomResponseDTO roomResponseDTO;

}
