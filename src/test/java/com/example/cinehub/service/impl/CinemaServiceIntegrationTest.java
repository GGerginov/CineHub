package com.example.cinehub.service.impl;

import com.example.cinehub.data.dtos.CinemaDto;
import com.example.cinehub.data.entity.Cinema;
import com.example.cinehub.exception.ApiException;
import com.example.cinehub.exception.jsonMessages.ErrorMessage;
import com.example.cinehub.repository.CinemaRepository;
import com.example.cinehub.service.CinemaService;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;



@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest
@Testcontainers
class CinemaServiceIntegrationTest {

    @Autowired
    private CinemaService cinemaService;

    @Autowired
    private CinemaRepository cinemaRepository;
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
    public void whenFindCinemasByTownName_thenCinemasShouldBeFound() throws ApiException {
        String cityName ="Sofia";
        List<Cinema> expected = this.cinemaRepository.findByCityNameIgnoreCase(cityName);

        List<CinemaDto> actual = cinemaService.findCinemasByTownName(cityName);

        assertEquals(expected.size(),actual.size());

        for (int i = 0; i < actual.size(); i++) {

            assertEquals(actual.get(i).getName(),expected.get(i).getName());
            assertEquals(actual.get(i).getId(),expected.get(i).getId());
            assertEquals(actual.get(i).getSlug(),expected.get(i).getSlug());
            assertEquals(actual.get(i).getAddress().getCityName(),expected.get(i).getAddress().getCityName());
            assertEquals(actual.get(i).getAddress().getStreet(),expected.get(i).getAddress().getStreet());
        }
    }

    @Test
    void whenFindCinemaByTownNameIsInvalid_thenShouldTrow() {

        String cityName = "invalid city name";

        ErrorMessage errorMessage = assertThrows(ApiException.class, () -> this.cinemaService.findCinemasByTownName(cityName))
                .getErrorMessage();

        assertEquals(errorMessage.getErrCode(), 404);
        assertEquals(errorMessage.getErrMsg(), "Cinema is not found");
    }


    @Test
    void whenFindAllCinemas_thenCinemasShouldBeReturned() {

        List<Cinema> expected = this.cinemaRepository.findAll();

        List<CinemaDto> actual = this.cinemaService.findAllCinemas();

        assertEquals(expected.size(),actual.size());


        for (int i = 0; i < actual.size(); i++) {

            assertEquals(actual.get(i).getName(),expected.get(i).getName());
            assertEquals(actual.get(i).getId(),expected.get(i).getId());
            assertEquals(actual.get(i).getSlug(),expected.get(i).getSlug());
            assertEquals(actual.get(i).getAddress().getCityName(),expected.get(i).getAddress().getCityName());
            assertEquals(actual.get(i).getAddress().getStreet(),expected.get(i).getAddress().getStreet());
        }
    }
}