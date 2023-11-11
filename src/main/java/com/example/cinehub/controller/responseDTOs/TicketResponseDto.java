package com.example.cinehub.controller.responseDTOs;

import com.example.cinehub.data.entity.TicketCategory;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO for {@link com.example.cinehub.data.entity.Ticket}
 */
@Builder
@Value
public class TicketResponseDto {

    @JsonProperty("id")
    UUID uuid;
    @JsonProperty("ticket_type")
    TicketCategory ticketType;

    @JsonProperty("ticket_price")
    Double price;

    @JsonProperty("show_time_start")
    @JsonFormat(pattern="yyyy-MM-dd")
    LocalDateTime showTimeStartTime;

    @JsonProperty("show_time_end")
    @JsonFormat(pattern="yyyy-MM-dd")
    LocalDateTime showTimeEndTime;

    @JsonProperty("movie_title")
    String showTimeMovieTitle;

    @JsonProperty("is_reserved")
    Boolean isReserved;

    @JsonProperty("seat_row")
    Integer seatRow;

    @JsonProperty("seatNumber")
    Integer seatNumber;

    @JsonProperty("room_number")
    Integer roomNumber;

    @JsonProperty("cinema_name")
    String cinemaName;

    @JsonProperty("cinema_city")
    String cinemaCity;

    @JsonProperty("cinema_street")
    String cinemaStreet;

    @JsonProperty("cinema_slug")
    String cinemaSlug;
}