package com.example.cinehub.controller.responseDTOs;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class RoomResponseDTO {

    @JsonProperty("room_number")
    Integer roomNumber;

    @JsonProperty("room_capacity")
    Integer capacity;

    @JsonProperty("cinema_slug")
    String cinemaSlug;

}
