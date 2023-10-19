package com.example.cinehub.controller.responseDTOs;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public class RoomResponseDTO {

    @JsonProperty("room_number")
    private Integer roomNumber;

    @JsonProperty("room_capacity")
    private Integer capacity;

    @JsonProperty("cinema_slug")
    private String cinemaSlug;


}
