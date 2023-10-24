package com.example.cinehub.controller.requestDTOs;

import com.example.cinehub.data.anotation.CinemaSlugExist;
import com.example.cinehub.data.anotation.MovieTitleExist;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;

@Getter
public class TicketCheckRequestDto {

    @JsonProperty("slug")
    @NotNull(message = "Cinema slug can not be null")
    @NotBlank(message = "Cinema slug can not be blank")
    @CinemaSlugExist
    private String slug;

    @JsonProperty("room_number")
    @NotNull(message = "Room number can not be null")
    @Positive(message = "Room number can not be negative or zero")
    private Integer roomNumber;

    @JsonProperty("movie_title")
    @NotNull(message = "Movie title can not be null")
    @NotBlank(message = "Movie title can not be blank")
    @MovieTitleExist
    private String movieTitle;

}
