package com.example.cinehub.controller.responseDTOs;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ShowTimeResponseDTOTest {

    @Test
    public void testGetStartTimeWhenStartTimeIsSetToValidTimeThenReturnStartTime() {
         
        ShowTimeResponseDTO showTimeResponseDTO = ShowTimeResponseDTO.builder()
                .startTime(LocalDateTime.of(2022, 1, 1, 10, 0))
                .build();

         
        LocalDateTime actualStartTime = showTimeResponseDTO.getStartTime();

         
        assertEquals(LocalDateTime.of(2022, 1, 1, 10, 0), actualStartTime);
    }

    @Test
    public void testGetEndTimeWhenEndTimeIsSetToValidTimeThenReturnEndTime() {
         
        ShowTimeResponseDTO showTimeResponseDTO = ShowTimeResponseDTO.builder()
                .endTime(LocalDateTime.of(2022, 1, 1, 12, 0))
                .build();

         
        LocalDateTime actualEndTime = showTimeResponseDTO.getEndTime();

         
        assertEquals(LocalDateTime.of(2022, 1, 1, 12, 0), actualEndTime);
    }

    @Test
    public void testGetMovieTitleWhenMovieTitleIsSetToValidTitleThenReturnMovieTitle() {
         
        ShowTimeResponseDTO showTimeResponseDTO = ShowTimeResponseDTO.builder()
                .movieTitle("Test Movie")
                .build();

         
        String actualMovieTitle = showTimeResponseDTO.getMovieTitle();

         
        assertEquals("Test Movie", actualMovieTitle);
    }

    @Test
    public void testGetMovieDurationWhenMovieDurationIsSetToValidDurationThenReturnMovieDuration() {
         
        ShowTimeResponseDTO showTimeResponseDTO = ShowTimeResponseDTO.builder()
                .movieDuration(120)
                .build();

         
        Integer actualMovieDuration = showTimeResponseDTO.getMovieDuration();

         
        assertEquals(120, actualMovieDuration);
    }

    @Test
    public void testEqualsWhenComparingTwoEqualShowTimeResponseDTOObjectsThenReturnTrue() {
          
        ShowTimeResponseDTO showTimeResponseDTO1 = ShowTimeResponseDTO.builder()
                .startTime(LocalDateTime.of(2022, 1, 1, 10, 0))
                .endTime(LocalDateTime.of(2022, 1, 1, 12, 0))
                .movieTitle("Test Movie")
                .movieDuration(120)
                .build();

        ShowTimeResponseDTO showTimeResponseDTO2 = ShowTimeResponseDTO.builder()
                .startTime(LocalDateTime.of(2022, 1, 1, 10, 0))
                .endTime(LocalDateTime.of(2022, 1, 1, 12, 0))
                .movieTitle("Test Movie")
                .movieDuration(120)
                .build();

         
        boolean result = showTimeResponseDTO1.equals(showTimeResponseDTO2);

         
        assertTrue(result);
    }

    @Test
    public void testEqualsWhenComparingTwoDifferentShowTimeResponseDTOObjectsThenReturnFalse() {
          
        ShowTimeResponseDTO showTimeResponseDTO1 = ShowTimeResponseDTO.builder()
                .startTime(LocalDateTime.of(2022, 1, 1, 10, 0))
                .endTime(LocalDateTime.of(2022, 1, 1, 12, 0))
                .movieTitle("Test Movie")
                .movieDuration(120)
                .build();

        ShowTimeResponseDTO showTimeResponseDTO2 = ShowTimeResponseDTO.builder()
                .startTime(LocalDateTime.of(2022, 1, 1, 14, 0))
                .endTime(LocalDateTime.of(2022, 1, 1, 16, 0))
                .movieTitle("Different Movie")
                .movieDuration(90)
                .build();

         
        boolean result = showTimeResponseDTO1.equals(showTimeResponseDTO2);

         
        assertFalse(result);
    }

    @Test
    public void testToStringWhenUsingToStringMethodThenReturnCorrectStringRepresentation() {
          
        ShowTimeResponseDTO showTimeResponseDTO = ShowTimeResponseDTO.builder()
                .startTime(LocalDateTime.of(2022, 1, 1, 10, 0))
                .endTime(LocalDateTime.of(2022, 1, 1, 12, 0))
                .movieTitle("Test Movie")
                .movieDuration(120)
                .build();
        String expectedStringRepresentation = "ShowTimeResponseDTO(startTime=2022-01-01T10:00, endTime=2022-01-01T12:00, movieTitle=Test Movie, movieDuration=120)";

         
        String actualStringRepresentation = showTimeResponseDTO.toString();

         
        assertEquals(expectedStringRepresentation, actualStringRepresentation);
    }

    @Test
    public void testHashCodeWhenComparingHashCodeOfEqualShowTimeResponseDTOObjectsThenReturnTrue() {
          
        ShowTimeResponseDTO showTimeResponseDTO1 = ShowTimeResponseDTO.builder()
                .startTime(LocalDateTime.of(2022, 1, 1, 10, 0))
                .endTime(LocalDateTime.of(2022, 1, 1, 12, 0))
                .movieTitle("Test Movie")
                .movieDuration(120)
                .build();

        ShowTimeResponseDTO showTimeResponseDTO2 = ShowTimeResponseDTO.builder()
                .startTime(LocalDateTime.of(2022, 1, 1, 10, 0))
                .endTime(LocalDateTime.of(2022, 1, 1, 12, 0))
                .movieTitle("Test Movie")
                .movieDuration(120)
                .build();

         
        int hashCode1 = showTimeResponseDTO1.hashCode();
        int hashCode2 = showTimeResponseDTO2.hashCode();

         
        assertEquals(hashCode1, hashCode2);
    }
}