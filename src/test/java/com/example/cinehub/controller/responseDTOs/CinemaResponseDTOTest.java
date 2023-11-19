package com.example.cinehub.controller.responseDTOs;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CinemaResponseDTOTest {


    @Test
    public void testGetNameWhenNameIsSetToCinemaNameThenReturnCinemaName() {
        CinemaResponseDTO cinemaResponseDTO = CinemaResponseDTO.builder()
                .name("Cinema Name")
                .build();

        String actualName = cinemaResponseDTO.getName();

        assertEquals("Cinema Name", actualName);
    }

    @Test
    public void testGetSlugWhenSlugIsSetToCinemaSlugThenReturnCinemaSlug() {
        CinemaResponseDTO cinemaResponseDTO = CinemaResponseDTO.builder()
                .slug("Cinema Slug")
                .build();

        String actualSlug = cinemaResponseDTO.getSlug();

        assertEquals("Cinema Slug", actualSlug);
    }

    @Test
    public void testGetCityNameWhenCityNameIsSetToCityNameThenReturnCityName() {
        CinemaResponseDTO cinemaResponseDTO = CinemaResponseDTO.builder()
                .cityName("City Name")
                .build();


        String actualCityName = cinemaResponseDTO.getCityName();

        assertEquals("City Name", actualCityName);
    }

    @Test
    public void testGetStreetNameWhenStreetNameIsSetToStreetNameThenReturnStreetName() {
        CinemaResponseDTO cinemaResponseDTO = CinemaResponseDTO.builder()
                .streetName("Street Name")
                .build();

        String actualStreetName = cinemaResponseDTO.getStreetName();

        assertEquals("Street Name", actualStreetName);
    }

    @Test
    public void testBuilderConstructorWhenUsingBuilderConstructorThenReturnCorrectValues() {
        CinemaResponseDTO cinemaResponseDTO = CinemaResponseDTO.builder()
                .name("Cinema Name")
                .slug("Cinema Slug")
                .cityName("City Name")
                .streetName("Street Name")
                .build();

        String actualName = cinemaResponseDTO.getName();
        String actualSlug = cinemaResponseDTO.getSlug();
        String actualCityName = cinemaResponseDTO.getCityName();
        String actualStreetName = cinemaResponseDTO.getStreetName();

        assertEquals("Cinema Name", actualName);
        assertEquals("Cinema Slug", actualSlug);
        assertEquals("City Name", actualCityName);
        assertEquals("Street Name", actualStreetName);
    }

    @Test
    public void testToStringWhenUsingToStringMethodThenReturnCorrectStringRepresentation() {
        CinemaResponseDTO cinemaResponseDTO = CinemaResponseDTO.builder()
                .name("Cinema Name")
                .slug("Cinema Slug")
                .cityName("City Name")
                .streetName("Street Name")
                .build();
        String expectedStringRepresentation = "CinemaResponseDTO(name=Cinema Name, slug=Cinema Slug, cityName=City Name, streetName=Street Name)";

        String actualStringRepresentation = cinemaResponseDTO.toString();

        assertEquals(expectedStringRepresentation, actualStringRepresentation);
    }


    @Test
    public void testEqualsWhenComparingTwoEqualCinemaResponseDTOObjectsThenReturnTrue() {
        CinemaResponseDTO cinemaResponseDTO1 = CinemaResponseDTO.builder()
                .name("Cinema Name")
                .slug("Cinema Slug")
                .cityName("City Name")
                .streetName("Street Name")
                .build();

        CinemaResponseDTO cinemaResponseDTO2 = CinemaResponseDTO.builder()
                .name("Cinema Name")
                .slug("Cinema Slug")
                .cityName("City Name")
                .streetName("Street Name")
                .build();

        boolean result = cinemaResponseDTO1.equals(cinemaResponseDTO2);

        assertTrue(result);
    }

    @Test
    public void testHashCodeWhenComparingHashCodeOfEqualCinemaResponseDTOObjectsThenReturnTrue() {

        CinemaResponseDTO cinemaResponseDTO1 = CinemaResponseDTO.builder()
                .name("Cinema Name")
                .slug("Cinema Slug")
                .cityName("City Name")
                .streetName("Street Name")
                .build();

        CinemaResponseDTO cinemaResponseDTO2 = CinemaResponseDTO.builder()
                .name("Cinema Name")
                .slug("Cinema Slug")
                .cityName("City Name")
                .streetName("Street Name")
                .build();

        int hashCode1 = cinemaResponseDTO1.hashCode();
        int hashCode2 = cinemaResponseDTO2.hashCode();


        assertEquals(hashCode1, hashCode2);
    }

}