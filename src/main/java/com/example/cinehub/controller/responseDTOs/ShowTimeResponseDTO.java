package com.example.cinehub.controller.responseDTOs;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

/**
 * DTO for {@link com.example.cinehub.data.entity.ShowTime}
 */
@Builder
@Value
public class ShowTimeResponseDTO{

    @JsonProperty("start_time")
    LocalDateTime startTime;

    @JsonProperty("end_time")
    LocalDateTime endTime;

    @JsonProperty("movie_title")
    String movieTitle;

    @JsonProperty("movie_duration")
    Integer movieDuration;
}