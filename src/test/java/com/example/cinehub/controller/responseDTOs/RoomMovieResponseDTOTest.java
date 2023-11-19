package com.example.cinehub.controller.responseDTOs;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RoomMovieResponseDTOTest {

    @Test
    public void testBuilderConstructorWhenUsingBuilderConstructorThenReturnCorrectValues() {
         RoomMovieResponseDTO roomMovieResponseDTO = RoomMovieResponseDTO.builder()
                .roomNumber(123)
                .capacity(100)
                .cinemaSlug("Test Slug")
                .build();

         Integer actualRoomNumber = roomMovieResponseDTO.getRoomNumber();
        Integer actualCapacity = roomMovieResponseDTO.getCapacity();
        String actualCinemaSlug = roomMovieResponseDTO.getCinemaSlug();

         assertEquals(123, actualRoomNumber);
        assertEquals(100, actualCapacity);
        assertEquals("Test Slug", actualCinemaSlug);
    }

    @Test
    public void testToStringWhenUsingToStringMethodThenReturnCorrectStringRepresentation() {
         RoomMovieResponseDTO roomMovieResponseDTO = RoomMovieResponseDTO.builder()
                .roomNumber(123)
                .capacity(100)
                .cinemaSlug("Test Slug")
                .build();
        String expectedStringRepresentation = "RoomMovieResponseDTO(roomNumber=123, capacity=100, cinemaSlug=Test Slug, showTimes=null)";

         String actualStringRepresentation = roomMovieResponseDTO.toString();

         assertEquals(expectedStringRepresentation, actualStringRepresentation);
    }

    @Test
    public void testHashCodeWhenComparingHashCodeOfEqualRoomMovieResponseDTOObjectsThenReturnTrue() {
         RoomMovieResponseDTO roomMovieResponseDTO1 = RoomMovieResponseDTO.builder()
                .roomNumber(123)
                .capacity(100)
                .cinemaSlug("Test Slug")
                .build();

        RoomMovieResponseDTO roomMovieResponseDTO2 = RoomMovieResponseDTO.builder()
                .roomNumber(123)
                .capacity(100)
                .cinemaSlug("Test Slug")
                .build();

         int hashCode1 = roomMovieResponseDTO1.hashCode();
        int hashCode2 = roomMovieResponseDTO2.hashCode();

         assertEquals(hashCode1, hashCode2);
    }

    @Test
    public void testEqualsWhenComparingTwoEqualRoomMovieResponseDTOObjectsThenReturnTrue() {
         RoomMovieResponseDTO roomMovieResponseDTO1 = RoomMovieResponseDTO.builder()
                .roomNumber(123)
                .capacity(100)
                .cinemaSlug("Test Slug")
                .build();

        RoomMovieResponseDTO roomMovieResponseDTO2 = RoomMovieResponseDTO.builder()
                .roomNumber(123)
                .capacity(100)
                .cinemaSlug("Test Slug")
                .build();

         boolean result = roomMovieResponseDTO1.equals(roomMovieResponseDTO2);

         assertTrue(result);
    }

    @Test
    public void testEqualsWhenComparingTwoDifferentRoomMovieResponseDTOObjectsThenReturnFalse() {
         RoomMovieResponseDTO roomMovieResponseDTO1 = RoomMovieResponseDTO.builder()
                .roomNumber(123)
                .capacity(100)
                .cinemaSlug("Test Slug")
                .build();

        RoomMovieResponseDTO roomMovieResponseDTO2 = RoomMovieResponseDTO.builder()
                .roomNumber(456)
                .capacity(200)
                .cinemaSlug("Different Slug")
                .build();

         boolean result = roomMovieResponseDTO1.equals(roomMovieResponseDTO2);

         assertFalse(result);
    }
}