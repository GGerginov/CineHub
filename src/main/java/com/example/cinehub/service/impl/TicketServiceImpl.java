package com.example.cinehub.service.impl;

import com.example.cinehub.data.dtos.TicketDto;
import com.example.cinehub.data.entity.Ticket;
import com.example.cinehub.repository.TicketRepository;
import com.example.cinehub.service.TicketService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;

@Service
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;

    private final ModelMapper modelMapper;


    @Autowired
    public TicketServiceImpl(TicketRepository ticketRepository, ModelMapper modelMapper) {
        this.ticketRepository = ticketRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public List<TicketDto> findAllTicketsByRoomNumberAndSlugAndMovieTitle(Integer roomNumber, String slug, String title) {

        List<Ticket> tickets = this.ticketRepository
                .findAllTicketsByRoomNumberAndSlugAndMovieTitle(roomNumber, slug, title);

        Type listOfMovieDtoType = new TypeToken<List<TicketDto>>() {
        }.getType();

        return this.modelMapper.map(tickets,listOfMovieDtoType);
    }
}
