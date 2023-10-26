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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Type;
import java.util.List;

@RestController
@RequestMapping("/api/ticket")
public class TicketController {

    private final TicketService ticketService;

    private final ModelMapper modelMapper;

    private final static Type LIST_OF_TICKET_RESPONSE_DTO_TYPE = new TypeToken<List<TicketResponseDto>>() {
    }.getType();

    @Autowired
    public TicketController(TicketService ticketService, ModelMapper modelMapper) {
        this.ticketService = ticketService;
        this.modelMapper = modelMapper;
    }


    @GetMapping("/check")
    public ResponseEntity<?> getAllTicketByCinemaSlugRoomNumberAndMovieTitle(@Validated @RequestBody TicketCheckRequestDto ticketCheckRequestDto
            , Errors errors) {


        if (errors.hasErrors()) {
            return new ErrorResponse(errors).getResponse();
        }

        List<TicketDto> tickets = this.ticketService.findAllTicketsByRoomNumberAndSlugAndMovieTitle(ticketCheckRequestDto.getRoomNumber()
                , ticketCheckRequestDto.getSlug()
                , ticketCheckRequestDto.getMovieTitle());

        List<TicketResponseDto> ticketsResponse = this.modelMapper.map(tickets, LIST_OF_TICKET_RESPONSE_DTO_TYPE);

        return new SuccessResponse<>(ticketsResponse).getResponse();
    }
}
