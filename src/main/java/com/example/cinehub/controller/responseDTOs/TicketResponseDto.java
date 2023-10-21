package com.example.cinehub.controller.responseDTOs;

import com.example.cinehub.data.entity.TicketCategory;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.time.LocalDateTime;

/**
 * DTO for {@link com.example.cinehub.data.entity.Ticket}
 */
@Builder
public class TicketResponseDto {

    @JsonProperty("ticket_type")
    TicketCategory ticketType;

    @JsonProperty("ticket_price")
    Double price;

    @JsonProperty("show_time_start")
    LocalDateTime showTimeStartTime;

    @JsonProperty("show_time_end")
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

    @JsonProperty("cinema_address")
    String cinemaAddress;

    @JsonProperty("cinema_slug")
    String cinemaSlug;
}