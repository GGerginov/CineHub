package com.example.cinehub.service.impl;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.example.cinehub.data.dtos.CinemaDto;
import com.example.cinehub.data.dtos.AddressDto;
import com.example.cinehub.data.entity.Address;
import com.example.cinehub.data.entity.Cinema;
import com.example.cinehub.exception.ApiException;
import com.example.cinehub.exception.jsonMessages.ErrorMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import com.example.cinehub.repository.CinemaRepository;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class CinemaServiceImplTest {

    @Mock
    private CinemaRepository cinemaRepository;

    @Mock
    private ModelMapper modelMapper;
    @InjectMocks
    private CinemaServiceImpl cinemaService;

    private final List<Cinema> expectedCinemas = new ArrayList<>();
    private final List<CinemaDto> expectedCinemaDTOs = new ArrayList<>();

    private String invalidTownName;


    @BeforeEach
    void setUp() {

        this.invalidTownName = "InvalidCityName";

        Cinema expectedCinema1 = new Cinema();
        expectedCinema1.setId(UUID.randomUUID());
        expectedCinema1.setName("Demo cinema1");
        expectedCinema1.setAddress(new Address());
        expectedCinema1.setSlug("demo-cinema-1");

        Cinema expectedCinema2 = new Cinema();
        expectedCinema2.setId(UUID.randomUUID());
        expectedCinema2.setName("Demo cinema2");
        expectedCinema2.setAddress(new Address());
        expectedCinema2.setSlug("demo-cinema-2");

        Cinema expectedCinema3 = new Cinema();
        expectedCinema3.setId(UUID.randomUUID());
        expectedCinema3.setName("Demo cinema3");
        expectedCinema3.setAddress(new Address());
        expectedCinema3.setSlug("demo-cinema-3");

        this.expectedCinemas.addAll(List.of(expectedCinema1, expectedCinema2, expectedCinema3));

        CinemaDto expectedCinemaDto1 = new CinemaDto(UUID.randomUUID(), "Demo cinema1", "Demo-cinema-1",new AddressDto());
        CinemaDto expectedCinemaDto2 = new CinemaDto(UUID.randomUUID(), "Demo cinema2", "Demo-cinema-2",new AddressDto());
        CinemaDto expectedCinemaDto3 = new CinemaDto(UUID.randomUUID(), "Demo cinema3", "Demo-cinema-3",new AddressDto());

        this.expectedCinemaDTOs.addAll(List.of(expectedCinemaDto1, expectedCinemaDto2, expectedCinemaDto3));
        lenient().when(modelMapper.map(any(), any(Type.class))).thenReturn(expectedCinemaDTOs);
    }

    @Test
    public void whenFindAllCinemas_thenCinemasShouldBeFound() {

        when(cinemaRepository.findAll())
                .thenReturn(expectedCinemas);

        List<CinemaDto> cinemasActual = cinemaService.findAllCinemas();

        assertFalse(cinemasActual.isEmpty());
        assertEquals(expectedCinemas.size(), cinemasActual.size());

        for (int i = 0; i < cinemasActual.size(); i++) {
            CinemaDto cinemaDto = cinemasActual.get(i);
            assertEquals(expectedCinemas.get(i).getName(), cinemaDto.getName());
            assertEquals(expectedCinemas.get(i).getAddress().getCityName(), cinemaDto.getAddress().getCityName());
        }
    }

    @Test
    public void whenFindCinemasByTownName_thenCinemaShouldBeFound() throws ApiException {

        when(cinemaRepository.findByCityNameIgnoreCase("validCityName"))
                .thenReturn(expectedCinemas);

        List<CinemaDto> cinemasActual = cinemaService.findCinemasByTownName("validCityName");

        assertFalse(cinemasActual.isEmpty());
        assertEquals(expectedCinemas.size(), cinemasActual.size());

        for (int i = 0; i < cinemasActual.size(); i++) {
            CinemaDto cinemaDto = cinemasActual.get(i);
            assertEquals(expectedCinemas.get(i).getName(), cinemaDto.getName());
            assertEquals(expectedCinemas.get(i).getAddress().getCityName(), cinemaDto.getAddress().getCityName());
        }
    }


    @Test
    public void whenFindCinemasByTownNameWithInvalidCityName_thenShouldTrow() {

        when(cinemaRepository.findByCityNameIgnoreCase(invalidTownName)).thenReturn(Collections.emptyList());

        ErrorMessage errorMessage = assertThrows(ApiException.class, () -> cinemaService.findCinemasByTownName(invalidTownName)).getErrorMessage();

        assertEquals(errorMessage.getErrCode(), 404);
        assertEquals(errorMessage.getErrMsg(), "Cinema is not found");
    }

}
