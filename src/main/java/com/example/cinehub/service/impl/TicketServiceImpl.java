package com.example.cinehub.service.impl;

import com.example.cinehub.data.dtos.TicketDto;
import com.example.cinehub.data.entity.Ticket;
import com.example.cinehub.exception.ApiException;
import com.example.cinehub.exception.jsonMessages.ErrorMessages;
import com.example.cinehub.repository.TicketRepository;
import com.example.cinehub.service.TicketService;
import jakarta.persistence.OptimisticLockException;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.lang.reflect.Type;
import java.util.List;
import java.util.UUID;

@Service
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;

    private final ModelMapper modelMapper;

    @Autowired
    public TicketServiceImpl(TicketRepository ticketRepository,
                             ModelMapper modelMapper){

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
    public Mono<TicketDto> bookTicketById(String id) {

        return Mono.fromCallable(() -> {
                    UUID uuid = validateUUID(id);

                    Ticket ticket = this.ticketRepository.findByIdForUpdate(uuid)
                            .orElseThrow(() -> new ApiException(ErrorMessages.TICKET_NOT_FOUND));

                    if (ticket.getIsReserved()) {
                        throw new ApiException(ErrorMessages.TICKET_IS_ALREADY_BOOKED);
                    }
                    ticket.setIsReserved(true);
                    try{
                        Ticket saved = this.ticketRepository.save(ticket);
                        return this.modelMapper.map(saved, TicketDto.class);
                    }catch (OptimisticLockException e){
                        throw new ApiException(ErrorMessages.TICKET_IS_ALREADY_BOOKED);
                    }
                })
                .subscribeOn(Schedulers.boundedElastic());
    }

    private UUID validateUUID(String id) throws ApiException {
        try {
            return UUID.fromString(id);
        } catch (IllegalArgumentException e) {
            throw new ApiException(ErrorMessages.TICKET_ID_NOT_VALID);
        }
    }

}
