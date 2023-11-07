package com.example.cinehub.controller.responseDTOs;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class CinemaResponseDTO {

    @JsonProperty("cinema_name")
    String name;

    @JsonProperty("cinema_address")
    String address;

    @JsonProperty("cinema_slug")
    String slug;

    @JsonProperty("city_name")
    String cityName;
}
