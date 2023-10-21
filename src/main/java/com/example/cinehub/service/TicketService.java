package com.example.cinehub.service;

import com.example.cinehub.data.dtos.TicketDto;

import java.util.List;


public interface TicketService {
    List<TicketDto> findAllTicketsByRoomNumberAndSlugAndMovieTitle(Integer roomNumber, String slug, String title);
}
