package com.example.cinehub.controller.responseDTOs;

import com.example.cinehub.data.entity.TicketCategory;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class TicketResponseDtoTest {

    @Test
    public void testGetUuidWhenUuidIsSetToValidUuidThenReturnUuid() {
        
        UUID uuid = UUID.randomUUID();
         TicketResponseDto ticketResponseDTO =  TicketResponseDto.builder()
                .uuid(uuid)
                .build();

        UUID actualUuid = ticketResponseDTO.getUuid();

        assertEquals(uuid, actualUuid);
    }

    @Test
    public void testGetTicketTypeWhenTicketTypeIsSetToValidTicketTypeThenReturnTicketType() {
        
        TicketCategory ticketType = TicketCategory.REGULAR;
         TicketResponseDto ticketResponseDTO =  TicketResponseDto.builder()
                .ticketType(ticketType)
                .build();

        TicketCategory actualTicketType = ticketResponseDTO.getTicketType();

        
        assertEquals(ticketType, actualTicketType);
    }

    @Test
    public void testGetPriceWhenPriceIsSetToValidPriceThenReturnPrice() {
        
        Double price = 10.0;
         TicketResponseDto ticketResponseDTO =  TicketResponseDto.builder()
                .price(price)
                .build();

        
        Double actualPrice = ticketResponseDTO.getPrice();

        
        assertEquals(price, actualPrice);
    }

    @Test
    public void testBuilderConstructorWhenUsingBuilderConstructorThenReturnCorrectValues() {
        
        UUID uuid = UUID.randomUUID();
        TicketCategory ticketType = TicketCategory.REGULAR;
        Double price = 10.0;
         TicketResponseDto ticketResponseDTO =  TicketResponseDto.builder()
                .uuid(uuid)
                .ticketType(ticketType)
                .price(price)
                .build();

        
        UUID actualUuid = ticketResponseDTO.getUuid();
        TicketCategory actualTicketType = ticketResponseDTO.getTicketType();
        Double actualPrice = ticketResponseDTO.getPrice();

        
        assertEquals(uuid, actualUuid);
        assertEquals(ticketType, actualTicketType);
        assertEquals(price, actualPrice);
    }
    @Test
    public void testEqualsWhenComparingTwoEqualTicketResponseDTOObjectsThenReturnTrue() {
        
        TicketResponseDto ticketResponseDTO1 = TicketResponseDto.builder()
                .ticketType(TicketCategory.REGULAR)
                .price(10.0)
                .showTimeStartTime(LocalDateTime.of(2022, 1, 1, 10, 0))
                .showTimeEndTime(LocalDateTime.of(2022, 1, 1, 12, 0))
                .showTimeMovieTitle("Test Movie")
                .isReserved(true)
                .seatRow(1)
                .seatNumber(2)
                .roomNumber(3)
                .cinemaName("Test Cinema")
                .cinemaCity("Test City")
                .cinemaStreet("Test Street")
                .cinemaSlug("Test Slug")
                .build();

         TicketResponseDto ticketResponseDTO2 =  TicketResponseDto.builder()
                .ticketType(TicketCategory.REGULAR)
                .price(10.0)
                .showTimeStartTime(LocalDateTime.of(2022, 1, 1, 10, 0))
                .showTimeEndTime(LocalDateTime.of(2022, 1, 1, 12, 0))
                .showTimeMovieTitle("Test Movie")
                .isReserved(true)
                .seatRow(1)
                .seatNumber(2)
                .roomNumber(3)
                .cinemaName("Test Cinema")
                .cinemaCity("Test City")
                .cinemaStreet("Test Street")
                .cinemaSlug("Test Slug")
                .build();

        
        boolean result = ticketResponseDTO1.equals(ticketResponseDTO2);

        
        assertTrue(result);
    }

    @Test
    public void testEqualsWhenComparingTwoDifferentTicketResponseDTOObjectsThenReturnFalse() {
        
         TicketResponseDto ticketResponseDTO1 =  TicketResponseDto.builder()
                .uuid(UUID.randomUUID())
                .ticketType(TicketCategory.REGULAR)
                .price(10.0)
                .showTimeStartTime(LocalDateTime.of(2022, 1, 1, 10, 0))
                .showTimeEndTime(LocalDateTime.of(2022, 1, 1, 12, 0))
                .showTimeMovieTitle("Test Movie")
                .isReserved(true)
                .seatRow(1)
                .seatNumber(2)
                .roomNumber(3)
                .cinemaName("Test Cinema")
                .cinemaCity("Test City")
                .cinemaStreet("Test Street")
                .cinemaSlug("Test Slug")
                .build();

         TicketResponseDto ticketResponseDTO2 =  TicketResponseDto.builder()
                .uuid(UUID.randomUUID())
                .ticketType(TicketCategory.VIP)
                .price(20.0)
                .showTimeStartTime(LocalDateTime.of(2022, 1, 1, 14, 0))
                .showTimeEndTime(LocalDateTime.of(2022, 1, 1, 16, 0))
                .showTimeMovieTitle("Different Movie")
                .isReserved(false)
                .seatRow(3)
                .seatNumber(4)
                .roomNumber(5)
                .cinemaName("Different Cinema")
                .cinemaCity("Different City")
                .cinemaStreet("Different Street")
                .cinemaSlug("Different Slug")
                .build();

        
        boolean result = ticketResponseDTO1.equals(ticketResponseDTO2);

        
        assertFalse(result);
    }

    @Test
    public void testToStringWhenUsingToStringMethodThenReturnCorrectStringRepresentation() {
        
         TicketResponseDto ticketResponseDTO =  TicketResponseDto.builder()
                .uuid(UUID.randomUUID())
                .ticketType(TicketCategory.REGULAR)
                .price(10.0)
                .showTimeStartTime(LocalDateTime.of(2022, 1, 1, 10, 0))
                .showTimeEndTime(LocalDateTime.of(2022, 1, 1, 12, 0))
                .showTimeMovieTitle("Test Movie")
                .isReserved(true)
                .seatRow(1)
                .seatNumber(2)
                .roomNumber(3)
                .cinemaName("Test Cinema")
                .cinemaCity("Test City")
                .cinemaStreet("Test Street")
                .cinemaSlug("Test Slug")
                .build();
        String expectedStringRepresentation = "TicketResponseDto(uuid=" + ticketResponseDTO.getUuid() +
                ", ticketType=REGULAR, price=10.0, showTimeStartTime=2022-01-01T10:00, showTimeEndTime=2022-01-01T12:00, " +
                "showTimeMovieTitle=Test Movie, isReserved=true, seatRow=1, seatNumber=2, roomNumber=3, " +
                "cinemaName=Test Cinema, cinemaCity=Test City, cinemaStreet=Test Street, cinemaSlug=Test Slug)";

        
        String actualStringRepresentation = ticketResponseDTO.toString();

        
        assertEquals(expectedStringRepresentation, actualStringRepresentation);
    }

    @Test
    public void testHashCodeWhenComparingHashCodeOfEqualTicketResponseDTOObjectsThenReturnTrue() {
        
         TicketResponseDto ticketResponseDTO1 =  TicketResponseDto.builder()
                .ticketType(TicketCategory.REGULAR)
                .price(10.0)
                .showTimeStartTime(LocalDateTime.of(2022, 1, 1, 10, 0))
                .showTimeEndTime(LocalDateTime.of(2022, 1, 1, 12, 0))
                .showTimeMovieTitle("Test Movie")
                .isReserved(true)
                .seatRow(1)
                .seatNumber(2)
                .roomNumber(3)
                .cinemaName("Test Cinema")
                .cinemaCity("Test City")
                .cinemaStreet("Test Street")
                .cinemaSlug("Test Slug")
                .build();

         TicketResponseDto ticketResponseDTO2 =  TicketResponseDto.builder()
                .ticketType(TicketCategory.REGULAR)
                .price(10.0)
                .showTimeStartTime(LocalDateTime.of(2022, 1, 1, 10, 0))
                .showTimeEndTime(LocalDateTime.of(2022, 1, 1, 12, 0))
                .showTimeMovieTitle("Test Movie")
                .isReserved(true)
                .seatRow(1)
                .seatNumber(2)
                .roomNumber(3)
                .cinemaName("Test Cinema")
                .cinemaCity("Test City")
                .cinemaStreet("Test Street")
                .cinemaSlug("Test Slug")
                .build();

        
        int hashCode1 = ticketResponseDTO1.hashCode();
        int hashCode2 = ticketResponseDTO2.hashCode();

        
        assertEquals(hashCode1, hashCode2);
    }
}