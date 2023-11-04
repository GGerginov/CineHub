package com.example.cinehub.controller.requestDTOs;

import com.example.cinehub.data.anotation.CinemaSlugExist;
import com.example.cinehub.data.anotation.MovieTitleExist;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record TicketCheckRequestDto(
        @JsonProperty("slug")
        @CinemaSlugExist
        @NotNull(message = "Cinema slug can not be null")
        @NotBlank(message = "Cinema slug can not be blank") String slug,
        @JsonProperty("room_number")
        @NotNull(message = "Room number can not be null")
        @Positive(message = "Room number can not be negative or zero") Integer roomNumber,
        @JsonProperty("movie_title")
        @MovieTitleExist
        @NotNull(message = "Movie title can not be null")
        @NotBlank(message = "Movie title can not be blank") String movieTitle) {

}
