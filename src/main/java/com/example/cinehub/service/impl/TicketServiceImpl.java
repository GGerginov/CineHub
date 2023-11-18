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
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.lang.reflect.Type;
import java.util.List;
import java.util.UUID;

/**
 * Service implementation for ticket-related operations in the CineHub application.
 * <p>
 * This service provides methods for retrieving ticket information and booking tickets.
 * It uses caching to improve performance and handles concurrent updates using optimistic locking.
 */
@Service
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;

    private final ModelMapper modelMapper;

    /**
     * Constructs a TicketServiceImpl with the specified ticket repository and model mapper.
     *
     * @param ticketRepository The repository for handling ticket-related data operations.
     * @param modelMapper      The model mapper for converting between DTOs and entities.
     */
    @Autowired
    public TicketServiceImpl(TicketRepository ticketRepository,
                             ModelMapper modelMapper){

        this.ticketRepository = ticketRepository;
        this.modelMapper = modelMapper;
    }


    /**
     * Retrieves a list of tickets based on room number, cinema slug, and movie title.
     * <p>
     * This method returns a list of TicketDto objects representing tickets that match the given criteria.
     * The result is cached for improved performance.
     *
     * @param roomNumber The room number of the cinema.
     * @param slug       The slug of the cinema.
     * @param title      The title of the movie.
     * @return A list of TicketDto objects.
     */
    @Override
    @Cacheable(cacheNames = "tickets", key = "#roomNumber + '-' + #slug + '-' + #title", unless = "#result.isEmpty()")
    public List<TicketDto> findAllTicketsByRoomNumberAndSlugAndMovieTitle(Integer roomNumber, String slug, String title) {

        List<Ticket> tickets = this.ticketRepository
                .findAllTicketsByRoomNumberAndSlugAndMovieTitle(roomNumber, slug, title);

        Type listOfMovieDtoType = new TypeToken<List<TicketDto>>() {
        }.getType();

        return this.modelMapper.map(tickets, listOfMovieDtoType);
    }

    /**
     * Books a ticket by its ID.
     * <p>
     * This method attempts to book a ticket by marking it as reserved. It handles concurrent updates using
     * optimistic locking to prevent double booking. The ticket cache is evicted after a successful booking.
     *
     * @param id The ID of the ticket to be booked.
     * @return A Mono of TicketDto representing the booked ticket.
     */
    @Override
    @CacheEvict(cacheNames = "tickets", allEntries = true)
    @Transactional
    public Mono<TicketDto> bookTicketById(String id) {

        return Mono.fromCallable(() -> {
                    UUID uuid = validateUUID(id);

                    Ticket ticket = this.ticketRepository.findByIdForUpdate(uuid)
                            .orElseThrow(() -> new ApiException(ErrorMessages.TICKET_NOT_FOUND));

                    if (ticket.getIsReserved()) {
                        throw new ApiException(ErrorMessages.TICKET_IS_ALREADY_BOOKED);
                    }
                    ticket.setIsReserved(true);
                    try {
                        Ticket saved = this.ticketRepository.save(ticket);
                        return this.modelMapper.map(saved, TicketDto.class);
                    } catch (OptimisticLockException | ObjectOptimisticLockingFailureException exception) {
                        throw new ApiException(ErrorMessages.TICKET_IS_ALREADY_BOOKED);
                    }

            })
                .subscribeOn(Schedulers.boundedElastic());
    }

    /**
     * Validates the format of the given UUID string.
     * <p>
     * This method checks if the provided string is a valid UUID and returns the UUID if valid.
     * Throws an ApiException if the string is not a valid UUID.
     *
     * @param id The string to be validated as UUID.
     * @return The UUID object.
     * @throws ApiException if the string is not a valid UUID.
     */
    private UUID validateUUID(String id) throws ApiException {
        try {
            return UUID.fromString(id);
        } catch (IllegalArgumentException e) {
            throw new ApiException(ErrorMessages.TICKET_ID_NOT_VALID);
        }
    }

}
