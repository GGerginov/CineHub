package com.example.cinehub.service.impl;

import com.example.cinehub.data.dtos.TicketDto;
import com.example.cinehub.data.entity.Ticket;
import com.example.cinehub.exception.ApiException;
import com.example.cinehub.exception.jsonMessages.ErrorMessage;
import com.example.cinehub.repository.TicketRepository;
import com.example.cinehub.service.TicketService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest
@Testcontainers
class TicketServiceIntegrationTest {

    @Autowired
    private TicketService ticketService;

    @Autowired
    private TicketRepository ticketRepository;

    @Container
    public static GenericContainer<?> redis = new GenericContainer<>(DockerImageName.parse("redis:latest"))
            .withExposedPorts(6379)
            .waitingFor(Wait.forListeningPort());
    @DynamicPropertySource
    static void redisProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.redis.host", redis::getHost);
        registry.add("spring.data.redis.port", () -> redis.getMappedPort(6379).toString());
    }

    @BeforeEach
    void setUp() {
        redis.start();
    }

    @Test
    void whenBookTicketByIdWhenAlreadyBooked_shouldThrow() {

        Ticket ticket = this.ticketRepository.findAll().get(0);

        ticket.setIsReserved(true);

        this.ticketRepository.saveAndFlush(ticket);

        ErrorMessage errorMessage = assertThrows(ApiException.class, () ->
                this.ticketService.bookTicketById(ticket.getId().toString()))
                .getErrorMessage();

        assertEquals(409,errorMessage.getErrCode());
        assertEquals("Ticket is already booked",errorMessage.getErrMsg());
    }


    @Test
    void whenBookTicketByInvalidId_shouldThrow() {

        ErrorMessage errorMessage = assertThrows(ApiException.class, () ->
                this.ticketService.bookTicketById("invalid")
        ).getErrorMessage();

        assertEquals(422,errorMessage.getErrCode());
        assertEquals("Ticket id is not valid",errorMessage.getErrMsg());
    }

    @Test
    void whenBookTicketById_shouldReturn() throws ApiException {

        Ticket ticket = this.ticketRepository.findAll().get(0);

        TicketDto ticketDto = this.ticketService.bookTicketById(ticket.getId().toString());

        assertEquals(ticket.getId(),ticketDto.getId());
        assertTrue(ticketDto.getIsReserved());
    }

    @Test
    void  whenFindAllTicketsByRoomNumberAndSlugAndMovieTitle_thenTicketsShouldBeReturned() {
        
        int roomNumber = 1;
        String slug = "sofia-cinema-1";
        String title = "Adventure Land";

        List<Ticket> expected = this.ticketRepository
                .findAllTicketsByRoomNumberAndSlugAndMovieTitle(roomNumber,slug,title);

        List<TicketDto> actual = this.ticketService
                .findAllTicketsByRoomNumberAndSlugAndMovieTitle(roomNumber, slug, title);

        assertEquals(expected.size(),actual.size());

        for (int i = 0; i < actual.size(); i++) {

            assertEquals(expected.get(i).getId(),actual.get(i).getId());
            assertEquals(expected.get(i).getTicketType(),actual.get(i).getTicketType());
            assertEquals(expected.get(i).getSeat().getSeatNumber(),actual.get(i).getSeat().getSeatNumber());
            assertEquals(expected.get(i).getSeat().getRowNumber(),actual.get(i).getSeat().getRowNumber());
        }
        
        
    }
}