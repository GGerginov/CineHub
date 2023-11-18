package com.example.cinehub.controller;


import com.example.cinehub.controller.requestDTOs.TicketCheckRequestDto;
import com.example.cinehub.controller.responseDTOs.TicketResponseDto;
import com.example.cinehub.data.dtos.TicketDto;
import com.example.cinehub.exception.jsonMessages.errorResponse.ErrorResponse;
import com.example.cinehub.exception.jsonMessages.successResponse.SuccessResponse;
import com.example.cinehub.service.TicketService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Controller for handling ticket-related requests in the CineHub application.
 * <p>
 * This controller provides endpoints for checking available tickets based on certain criteria
 * and for booking tickets by their ID.
 */

@RestController
@RequestMapping("/api/ticket")
public class TicketController {

    private final TicketService ticketService;

    private final ModelMapper modelMapper;

    private final static Type LIST_OF_TICKET_RESPONSE_DTO_TYPE = new TypeToken<List<TicketResponseDto>>() {
    }.getType();


    /**
     * Constructs a TicketController with the specified ticket service and model mapper.
     *
     * @param ticketService The service for handling ticket-related operations.
     * @param modelMapper   The model mapper for converting between DTOs and entities.
     */
    @Autowired
    public TicketController(TicketService ticketService, ModelMapper modelMapper) {
        this.ticketService = ticketService;
        this.modelMapper = modelMapper;
    }


    /**
     * Retrieves all tickets based on cinema slug, room number, and movie title.
     * <p>
     * This method returns a list of tickets that match the specified criteria.
     *
     * @param ticketCheckRequestDto The DTO containing the criteria for ticket search.
     * @param errors                Binding result containing validation errors.
     * @return A ResponseEntity containing a list of TicketResponseDto or an error response.
     */
    @GetMapping("/check")
    public ResponseEntity<?> getAllTicketByCinemaSlugRoomNumberAndMovieTitle(@Validated @RequestBody TicketCheckRequestDto ticketCheckRequestDto
            , Errors errors) {

        if (errors.hasErrors()) {
            return new ErrorResponse(errors).getResponse();
        }

        List<TicketDto> tickets = this.ticketService.findAllTicketsByRoomNumberAndSlugAndMovieTitle(ticketCheckRequestDto.roomNumber()
                , ticketCheckRequestDto.slug()
                , ticketCheckRequestDto.movieTitle());

        List<TicketResponseDto> ticketsResponse = this.modelMapper.map(tickets, LIST_OF_TICKET_RESPONSE_DTO_TYPE);

        return new SuccessResponse<>(ticketsResponse).getResponse();
    }

    /**
     * Books a ticket by its ID.
     * <p>
     * This method updates the status of a ticket to 'booked' based on the provided ticket ID.
     *
     * @param id The ID of the ticket to be booked.
     * @return A Mono of ResponseEntity containing the booked TicketResponseDto or an error response.
     */
    @PutMapping("/book/{id}")
    public Mono<ResponseEntity<?>> bookTicketById(@PathVariable(name = "id") String id) {

        return ticketService.bookTicketById(id)
                .subscribeOn(Schedulers.boundedElastic())
                .map(ticketDto -> {
                    TicketResponseDto ticketResponseDto = modelMapper.map(ticketDto, TicketResponseDto.class);
                    return new SuccessResponse<>(ticketResponseDto).getResponse();
                });
    }
}
