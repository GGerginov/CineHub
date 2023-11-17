package com.example.cinehub.controller;

import com.example.cinehub.data.entity.Cinema;
import com.example.cinehub.repository.CinemaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Testcontainers
class CinemaControllerTest {


    @Autowired
    WebApplicationContext wac;

    @Autowired
    CinemaRepository cinemaRepository;
    private MockMvc mockMvc;
    private String endPoint;

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
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
        MockitoAnnotations.openMocks(this);
        this.endPoint = "/api/cinemas";
        redis.start();
    }

    @Test
    void testGetAllCinemas() throws Exception {

        List<Cinema> all = this.cinemaRepository.findAll();

        ResultActions resultActions = this.mockMvc.perform(get(endPoint))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success", is(true)));


        for (int i = 0; i < all.size(); i++) {

            Cinema currentCinema = all.get(i);
            resultActions
                    .andExpect(jsonPath("$.data[" + i + "].cinema_name", is(currentCinema.getName())))
                    .andExpect(jsonPath("$.data[" + i + "].cinema_slug", is(currentCinema.getSlug())))
                    .andExpect(jsonPath("$.data[" + i + "].city_name", is(currentCinema.getAddress().getCityName())))
                    .andExpect(jsonPath("$.data[" + i + "].street_name", is(currentCinema.getAddress().getStreet())));
        }
    }


    @Test
    public void testGetCinemasByCityName() throws Exception {

        String validCityName = this.cinemaRepository.findAll().get(0).getAddress().getCityName();

        List<Cinema> all = this.cinemaRepository.findByCityNameIgnoreCase(validCityName);

        ResultActions resultActions = this.mockMvc.perform(get(endPoint + "/city/" + validCityName))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success", is(true)));


        for (int i = 0; i < all.size(); i++) {

            Cinema currentCinema = all.get(i);
            resultActions
                    .andExpect(jsonPath("$.data[" + i + "].cinema_name", is(currentCinema.getName())))
                    .andExpect(jsonPath("$.data[" + i + "].cinema_slug", is(currentCinema.getSlug())))
                    .andExpect(jsonPath("$.data[" + i + "].city_name", is(currentCinema.getAddress().getCityName())))
                    .andExpect(jsonPath("$.data[" + i + "].street_name", is(currentCinema.getAddress().getStreet())));
        }
    }

    @Test
    public void testGetCinemasByInvalidCityNameShouldThrow() throws Exception {

        this.mockMvc.perform(get(endPoint + "/city/InvalidCityName"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success", is(false)))
                .andExpect(jsonPath("$.errors[0].errCode",is(404)))
                .andExpect(jsonPath("$.errors[0].errMsg",is("Cinema is not found")));
    }

}