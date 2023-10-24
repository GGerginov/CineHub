package com.example.cinehub.controller;


import com.example.cinehub.controller.requestDTOs.TicketCheckRequestDto;
import com.example.cinehub.controller.responseDTOs.TicketResponseDto;
import com.example.cinehub.data.dtos.TicketDto;
import com.example.cinehub.exception.jsonMessages.errorResponse.ErrorResponse;
import com.example.cinehub.exception.jsonMessages.successResponse.SuccessResponse;
import com.example.cinehub.service.TicketService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/ticket")
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping("/check")
    public ResponseEntity<?> getAllTicketByCinemaSlugRoomNumberAndMovieTitle(@Validated @RequestBody TicketCheckRequestDto ticketCheckRequestDto
    , Errors errors){


        if (errors.hasErrors()) {
            return new ErrorResponse(errors).getResponse();
        }

        List<TicketDto> tickets = this.ticketService.findAllTicketsByRoomNumberAndSlugAndMovieTitle(ticketCheckRequestDto.getRoomNumber()
                , ticketCheckRequestDto.getSlug()
                , ticketCheckRequestDto.getMovieTitle());


        List<TicketResponseDto> ticketsResponse = tickets.stream().map(ticketDto -> TicketResponseDto.builder()
                        .ticketType(ticketDto.getTicketType())
                        .price(ticketDto.getPrice())
                        .showTimeStartTime(ticketDto.getShowTime().getStartTime())
                        .showTimeEndTime(ticketDto.getShowTime().getEndTime())
                        .showTimeMovieTitle(ticketDto.getShowTime().getMovie().getTitle())
                        .isReserved(ticketDto.getIsReserved())
                        .seatNumber(ticketDto.getSeat().getSeatNumber())
                        .seatRow(ticketDto.getSeat().getRowNumber())
                        .roomNumber(ticketDto.getSeat().getRoom().getRoomNumber())
                        .cinemaName(ticketDto.getSeat().getRoom().getCinema().getName())
                        .cinemaAddress(ticketDto.getSeat().getRoom().getCinema().getAddress())
                        .cinemaSlug(ticketDto.getSeat().getRoom().getCinema().getSlug())
                        .build())
                .toList();

        return new SuccessResponse<>(ticketsResponse).getResponse();
    }
}
