package com.example.cinehub.service.impl;

import com.example.cinehub.data.dtos.SeatDto;
import com.example.cinehub.data.dtos.ShowTimeDto;
import com.example.cinehub.data.dtos.TicketDto;
import com.example.cinehub.data.entity.Seat;
import com.example.cinehub.data.entity.ShowTime;
import com.example.cinehub.data.entity.Ticket;
import com.example.cinehub.data.entity.TicketCategory;
import com.example.cinehub.exception.ApiException;
import com.example.cinehub.exception.jsonMessages.ErrorMessage;
import com.example.cinehub.repository.TicketRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TicketServiceImplTest {

    @Mock
    private TicketRepository ticketRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private TicketServiceImpl ticketService;

    private final List<Ticket> expectedTickets = new ArrayList<>();

    private final List<TicketDto> expectedTicketDtos = new ArrayList<>();
    @BeforeEach
    void setUp() {

        Ticket ticket1 = new Ticket();
        ticket1.setId(UUID.randomUUID());
        ticket1.setPrice(20.0);
        ticket1.setIsReserved(false);
        ticket1.setTicketType(TicketCategory.REGULAR);
        ticket1.setSeat(new Seat());
        ticket1.setShowTime(new ShowTime());

        Ticket ticket2 = new Ticket();
        ticket2.setId(UUID.randomUUID());
        ticket2.setPrice(20.0);
        ticket2.setIsReserved(false);
        ticket2.setTicketType(TicketCategory.REGULAR);
        ticket2.setSeat(new Seat());
        ticket2.setShowTime(new ShowTime());

        Ticket ticket3 = new Ticket();
        ticket3.setId(UUID.randomUUID());
        ticket3.setPrice(20.0);
        ticket3.setIsReserved(false);
        ticket3.setTicketType(TicketCategory.REGULAR);
        ticket3.setSeat(new Seat());
        ticket3.setShowTime(new ShowTime());

        this.expectedTickets.addAll(List.of(ticket1,ticket2,ticket3));

        TicketDto ticketDto1 = new TicketDto(ticket1.getId(),ticket1.getTicketType(),ticket1.getPrice()
                ,new ShowTimeDto(),ticket1.getIsReserved(),new SeatDto());

        TicketDto ticketDto2 = new TicketDto(ticket2.getId(),ticket2.getTicketType(),ticket2.getPrice()
                ,new ShowTimeDto(),ticket2.getIsReserved(),new SeatDto());

        TicketDto ticketDto3 = new TicketDto(ticket3.getId(),ticket3.getTicketType(),ticket3.getPrice()
                ,new ShowTimeDto(),ticket3.getIsReserved(),new SeatDto());

        this.expectedTicketDtos.addAll(List.of(ticketDto1,ticketDto2,ticketDto3));

        lenient().when(modelMapper.map(any(), any(Type.class))).thenReturn(expectedTicketDtos);
    }

    @Test
    public void whenFindAllTicketsByRoomNumberAndSlugAndMovieTitle_thenTicketsShouldBeReturned() {

        Integer roomNumber = 1;
        String slug = "demo-cinema-1";
        String title = "demo-movie";

        when(ticketRepository.findAllTicketsByRoomNumberAndSlugAndMovieTitle(roomNumber, slug, title))
                .thenReturn(expectedTickets);

        List<TicketDto> ticketDtos = ticketService.findAllTicketsByRoomNumberAndSlugAndMovieTitle(roomNumber, slug, title);

        assertEquals(expectedTicketDtos.size(), ticketDtos.size());

        for (int i = 0; i < ticketDtos.size(); i++) {
            TicketDto actualTicketDto = ticketDtos.get(i);

            assertEquals(expectedTicketDtos.get(i).getId(),actualTicketDto.getId());
            assertEquals(expectedTicketDtos.get(i).getPrice(),actualTicketDto.getPrice());
            assertEquals(expectedTicketDtos.get(i).getTicketType(),actualTicketDto.getTicketType());
            assertEquals(expectedTicketDtos.get(i).getSeat(),actualTicketDto.getSeat());
            assertEquals(expectedTicketDtos.get(i).getShowTime(),actualTicketDto.getShowTime());
            assertEquals(expectedTicketDtos.get(i).getIsReserved(),actualTicketDto.getIsReserved());
        }
    }

    @Test
    public void whenBookTicketByIdWithValidId_thenTicketShouldBeBooked() throws ApiException {
        Ticket ticket = expectedTickets.get(0);

        when(ticketRepository.findById(UUID.fromString(ticket.getId().toString()))).thenReturn(Optional.of(ticket));

        when(ticketRepository.saveAndFlush(any(Ticket.class))).thenReturn(ticket);

        TicketDto expectedTicketDto = expectedTicketDtos.get(0);
        when(modelMapper.map(ticket, TicketDto.class)).thenReturn(expectedTicketDto);

        TicketDto actualTicketDto = ticketService.bookTicketById(ticket.getId().toString());

        assertEquals(expectedTicketDto.getId(),actualTicketDto.getId());
        assertEquals(expectedTicketDto.getPrice(),actualTicketDto.getPrice());
        assertEquals(expectedTicketDto.getTicketType(),actualTicketDto.getTicketType());
        assertEquals(expectedTicketDto.getSeat(),actualTicketDto.getSeat());
        assertEquals(expectedTicketDto.getShowTime(),actualTicketDto.getShowTime());
        assertEquals(expectedTicketDto.getIsReserved(),actualTicketDto.getIsReserved());
    }


    @Test
    public void whenBookTicketByIdWithInvalidId_thenShouldTrow() throws ApiException {
        Ticket ticket = expectedTickets.get(0);


        when(ticketRepository.findById(UUID.fromString(ticket.getId().toString()))).thenReturn(Optional.of(ticket));

        when(ticketRepository.saveAndFlush(any(Ticket.class))).thenReturn(ticket);

        TicketDto expectedTicketDto = expectedTicketDtos.get(0);
        when(modelMapper.map(ticket, TicketDto.class)).thenReturn(expectedTicketDto);

        TicketDto actualTicketDto = ticketService.bookTicketById(ticket.getId().toString());

        assertEquals(expectedTicketDto.getId(),actualTicketDto.getId());
        assertEquals(expectedTicketDto.getPrice(),actualTicketDto.getPrice());
        assertEquals(expectedTicketDto.getTicketType(),actualTicketDto.getTicketType());
        assertEquals(expectedTicketDto.getSeat(),actualTicketDto.getSeat());
        assertEquals(expectedTicketDto.getShowTime(),actualTicketDto.getShowTime());
        assertEquals(expectedTicketDto.getIsReserved(),actualTicketDto.getIsReserved());
    }


    @Test
    public void whenBookTicketByIdWithInvalidId_thenExceptionShouldBeThrown() {
        String invalidId = "invalid-uuid";

        ErrorMessage errorMessage = assertThrows(ApiException.class, () -> ticketService.bookTicketById(invalidId))
                .getErrorMessage();

        assertEquals(422,errorMessage.getErrCode());
        assertEquals("Ticket id is not valid",errorMessage.getErrMsg());
    }


    @Test
    public void whenBookTicketByIdWhenIdNotExist_thenExceptionShouldBeThrown() {

        ErrorMessage errorMessage = assertThrows(ApiException.class, () -> ticketService.bookTicketById(UUID.randomUUID().toString())).getErrorMessage();

        assertEquals(404,errorMessage.getErrCode());
        assertEquals("Ticket is not found",errorMessage.getErrMsg());
    }


    @Test
    public void whenBookTicketByIdWhenIsAlreadyBooked_thenExceptionShouldBeThrown() {

        Ticket ticket = expectedTickets.get(0);
        ticket.setIsReserved(true);

        when(ticketRepository.findById(any())).thenReturn(Optional.of(ticket));

        ErrorMessage errorMessage = assertThrows(ApiException.class, () -> ticketService.bookTicketById(UUID.randomUUID().toString())).getErrorMessage();

        assertEquals(409,errorMessage.getErrCode());
        assertEquals("Ticket is already booked",errorMessage.getErrMsg());
    }

}
