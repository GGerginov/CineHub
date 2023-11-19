package com.example.cinehub.data.dtos;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class SeatDtoTest {

    private UUID id ;
    private Integer rowNumber;
    private Integer seatNumber;

    private RoomDto roomDto;

    @BeforeEach
    void setUp() {

        this.id = UUID.randomUUID();
        this.rowNumber=1;
        this.seatNumber=2;
        this.roomDto = new RoomDto();
    }

    @Test
    public void testNoArgsConstructorWhenUsingNoArgsConstructorThenCreateSeatDtoWithDefaultValues() {
        SeatDto seatDto = new SeatDto();

        assertNull(seatDto.getId());
        assertNull(seatDto.getRowNumber());
        assertNull(seatDto.getSeatNumber());
        assertNull(seatDto.getRoom());
    }

    @Test
    public void testAllArgsConstructorWhenUsingAllArgsConstructorThenCreateSeatDtoWithGivenValues() {
        SeatDto seatDto = new SeatDto(id, rowNumber, seatNumber, roomDto);

        assertEquals(id, seatDto.getId());
        assertEquals(rowNumber, seatDto.getRowNumber());
        assertEquals(seatNumber, seatDto.getSeatNumber());
        assertEquals(roomDto, seatDto.getRoom());
    }

    @Test
    void testBuilderSetsCorrectValues() {

        SeatDto seatDto = SeatDto.builder()
                .id(this.id)
                .seatNumber(this.seatNumber)
                .rowNumber(this.rowNumber)
                .room(this.roomDto)
                .build();

        assertEquals(id, seatDto.getId());
        assertEquals(rowNumber, seatDto.getRowNumber());
        assertEquals(seatNumber, seatDto.getSeatNumber());
        assertEquals(roomDto, seatDto.getRoom());
    }
}