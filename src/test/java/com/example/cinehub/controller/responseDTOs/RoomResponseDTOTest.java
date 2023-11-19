package com.example.cinehub.controller.responseDTOs;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoomResponseDTOTest {


    @Test
    public void testGetRoomNumberWhenRoomNumberIsSetTo123ThenReturn123() {
         
        RoomResponseDTO roomResponseDTO = RoomResponseDTO.builder()
                .roomNumber(123)
                .build();

        Integer actualRoomNumber = roomResponseDTO.getRoomNumber();

         
        assertEquals(123, actualRoomNumber);
    }

    @Test
    public void testGetCapacityWhenCapacityIsSetTo100ThenReturn100() {
         
        RoomResponseDTO roomResponseDTO = RoomResponseDTO.builder()
                .capacity(100)
                .build();

         
        Integer actualCapacity = roomResponseDTO.getCapacity();

         
        assertEquals(100, actualCapacity);
    }

    @Test
    public void testGetCinemaSlugWhenCinemaSlugIsSetToTestSlugThenReturnTestSlug() {
         
        RoomResponseDTO roomResponseDTO = RoomResponseDTO.builder()
                .cinemaSlug("Test Slug")
                .build();

         
        String actualCinemaSlug = roomResponseDTO.getCinemaSlug();

         
        assertEquals("Test Slug", actualCinemaSlug);
    }

    @Test
    public void testEqualsWhenComparingTwoEqualRoomResponseDTOObjectsThenReturnTrue() {
         
        RoomResponseDTO roomResponseDTO1 = RoomResponseDTO.builder()
                .roomNumber(123)
                .capacity(100)
                .cinemaSlug("Test Slug")
                .build();

        RoomResponseDTO roomResponseDTO2 = RoomResponseDTO.builder()
                .roomNumber(123)
                .capacity(100)
                .cinemaSlug("Test Slug")
                .build();

         
        boolean result = roomResponseDTO1.equals(roomResponseDTO2);

         
        assertTrue(result);
    }

    @Test
    public void testEqualsWhenComparingTwoDifferentRoomResponseDTOObjectsThenReturnFalse() {
         
        RoomResponseDTO roomResponseDTO1 = RoomResponseDTO.builder()
                .roomNumber(123)
                .capacity(100)
                .cinemaSlug("Test Slug")
                .build();

        RoomResponseDTO roomResponseDTO2 = RoomResponseDTO.builder()
                .roomNumber(456)
                .capacity(200)
                .cinemaSlug("Different Slug")
                .build();

         
        boolean result = roomResponseDTO1.equals(roomResponseDTO2);

         
        assertFalse(result);
    }

    @Test
    public void testToStringWhenUsingToStringMethodThenReturnCorrectStringRepresentation() {
         
        RoomResponseDTO roomResponseDTO = RoomResponseDTO.builder()
                .roomNumber(123)
                .capacity(100)
                .cinemaSlug("Test Slug")
                .build();
        String expectedStringRepresentation = "RoomResponseDTO(roomNumber=123, capacity=100, cinemaSlug=Test Slug)";

         
        String actualStringRepresentation = roomResponseDTO.toString();

         
        assertEquals(expectedStringRepresentation, actualStringRepresentation);
    }

    @Test
    public void testHashCodeWhenComparingHashCodeOfEqualRoomResponseDTOObjectsThenReturnTrue() {
         
        RoomResponseDTO roomResponseDTO1 = RoomResponseDTO.builder()
                .roomNumber(123)
                .capacity(100)
                .cinemaSlug("Test Slug")
                .build();

        RoomResponseDTO roomResponseDTO2 = RoomResponseDTO.builder()
                .roomNumber(123)
                .capacity(100)
                .cinemaSlug("Test Slug")
                .build();

         
        int hashCode1 = roomResponseDTO1.hashCode();
        int hashCode2 = roomResponseDTO2.hashCode();

         
        assertEquals(hashCode1, hashCode2);
    }


}