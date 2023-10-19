package com.example.cinehub.controller.responseDTOs;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public class CinemaResponseDTO {

    @JsonProperty("cinema_name")
    private String name;

    @JsonProperty("cinema_address")
    private String address;

    @JsonProperty("cinema_slug")
    private String slug;

    @JsonProperty("city_name")
    private String cityName;
}
