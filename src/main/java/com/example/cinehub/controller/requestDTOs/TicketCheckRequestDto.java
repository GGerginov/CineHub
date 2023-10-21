package com.example.cinehub.controller.requestDTOs;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class TicketCheckRequestDto {

    @JsonProperty("slug")
    private String slug;

    @JsonProperty("room_number")
    private Integer roomNumber;

    @JsonProperty("movie_title")
    private String movieTitle;

}
