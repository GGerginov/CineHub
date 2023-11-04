package com.example.cinehub.service.impl;

import com.example.cinehub.data.dtos.TicketDto;
import com.example.cinehub.data.entity.Ticket;
import com.example.cinehub.exception.ApiException;
import com.example.cinehub.exception.jsonMessages.ErrorMessages;
import com.example.cinehub.repository.TicketRepository;
import com.example.cinehub.service.TicketService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
    @Cacheable(cacheNames = "tickets", key = "#roomNumber + '-' + #slug + '-' + #title", unless = "#result.isEmpty()")
    public List<TicketDto> findAllTicketsByRoomNumberAndSlugAndMovieTitle(Integer roomNumber, String slug, String title) {

        List<Ticket> tickets = this.ticketRepository
                .findAllTicketsByRoomNumberAndSlugAndMovieTitle(roomNumber, slug, title);

        Type listOfMovieDtoType = new TypeToken<List<TicketDto>>() {
        }.getType();

        return this.modelMapper.map(tickets, listOfMovieDtoType);
    }

    @Override
    @CacheEvict(cacheNames = "tickets", allEntries = true)
    public synchronized TicketDto bookTicketById(String id) throws ApiException {

        Optional<Ticket> optional = this.ticketRepository.findById(UUID.fromString(id));

        Ticket ticket = optional.get();

        if (ticket.getIsReserved()) throw new ApiException(ErrorMessages.TICKET_IS_ALREADY_BOOKED);

        ticket.setIsReserved(true);

        return modelMapper.map(this.ticketRepository.saveAndFlush(ticket), TicketDto.class);
    }
}
