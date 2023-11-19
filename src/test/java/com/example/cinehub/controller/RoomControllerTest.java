package com.example.cinehub.controller;

import com.example.cinehub.data.entity.Room;
import com.example.cinehub.data.entity.ShowTime;
import com.example.cinehub.repository.RoomRepository;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
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

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Testcontainers
class RoomControllerTest {


    @Autowired
    WebApplicationContext wac;

    @Autowired
    RoomRepository roomRepository;
    private MockMvc mockMvc;
    private String endPoint;

    private int roomNumber ;
    private String cinemaSlug ;
    private LocalDateTime startTime ;
    private LocalDateTime endTime ;
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
        this.endPoint = "/api/rooms/";
        this.roomNumber = 2;
        this.cinemaSlug= "sofia-cinema-1";
        this.startTime= LocalDateTime.of(LocalDate.now().plusDays(1), LocalTime.of(12, 0));
        this.endTime =LocalDateTime.of(LocalDate.now().plusDays(1), LocalTime.of(15, 0));
        redis.start();
    }

    @Test
    void testGetAllRoomsByCinemaSlug() throws Exception {

        String validSlug = this.roomRepository.findAll().get(0).getCinema().getSlug();

        ResultActions resultActions = this.mockMvc.perform(get(this.endPoint + validSlug))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success", is(true)));

        List<Room> roomsExpected = this.roomRepository.findAllRoomsByCinemaSlug(validSlug);

        for (int i = 0; i < roomsExpected.size(); i++) {

            Room currentExpected = roomsExpected.get(i);

            resultActions
                    .andExpect(jsonPath("$.data[" + i + "].room_number", is(currentExpected.getRoomNumber())))
                    .andExpect(jsonPath("$.data[" + i + "].room_capacity", is(currentExpected.getCapacity())))
                    .andExpect(jsonPath("$.data[" + i + "].cinema_slug", is(currentExpected.getCinema().getSlug())));
        }
    }

    @Test
    void testGetAllRoomsByInvalidCinemaSlugShouldThrow() throws Exception {

        this.mockMvc.perform(get(this.endPoint + "invalidSlug"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success", is(false)))
                .andExpect(jsonPath("$.errors[0].errCode", is(404)))
                .andExpect(jsonPath("$.errors[0].errMsg", is("Slug is not found")));
    }

    @Test
    void listAllUpcomingBroadcasts() throws Exception {

        ResultActions resultActions = this.mockMvc.perform(get(this.endPoint + "/upcoming-broadcasts"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success", is(true)));

        List<Room> roomsExpected = this.roomRepository.findAllRoomsWithUpcomingBroadcasts();

        for (int i = 0; i < roomsExpected.size(); i++) {

            Room currentExpected = roomsExpected.get(i);

            resultActions
                    .andExpect(jsonPath("$.data[" + i + "].room_number", is(currentExpected.getRoomNumber())))
                    .andExpect(jsonPath("$.data[" + i + "].room_capacity", is(currentExpected.getCapacity())))
                    .andExpect(jsonPath("$.data[" + i + "].cinema_slug", is(currentExpected.getCinema().getSlug())));
        }
    }

    @Test
    @Order(1)
    void findAllActualMoviesForARoomInTimeRange() throws Exception {

        Room moviesInRange = this.roomRepository
                .findRoomWithMoviesInRange(roomNumber, cinemaSlug, startTime, endTime).get();

        String requestBody = new JSONObject()
                .put("cinema_slug", cinemaSlug)
                .put("room_number", roomNumber)
                .put("start_time", startTime)
                .put("end_time", endTime).toString();

        ResultActions resultActions = this.mockMvc.perform(get(endPoint + "/movies-in-room-for-period")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.data.room_number", is(moviesInRange.getRoomNumber())))
                .andExpect(jsonPath("$.data.room_capacity", is(moviesInRange.getCapacity())))
                .andExpect(jsonPath("$.data.cinema_slug", is(moviesInRange.getCinema().getSlug())));

        List<ShowTime> showTimes = moviesInRange.getShowTimes();
        for (int i = 0; i < showTimes.size(); i++) {
            ShowTime current = showTimes.get(i);
            resultActions
                    .andExpect(jsonPath("$.data.show_times" + "[" + i + "]" + ".start_time", is(this.parseTimeStampString(current.getStartTime()))))
                    .andExpect(jsonPath("$.data.show_times" + "[" + i + "]" + ".end_time", is(this.parseTimeStampString(current.getEndTime()))))
                    .andExpect(jsonPath("$.data.show_times" + "[" + i + "]" + ".movie_title", is(current.getMovie().getTitle())))
                    .andExpect(jsonPath("$.data.show_times" + "[" + i + "]" + ".movie_duration", is(current.getMovie().getDuration())));
        }
    }


    @Test
    public void findAllActualMoviesForARoomInTimeRangeWithInvalidTime_ShouldThrow() throws Exception {

        String requestBody = new JSONObject()
                .put("cinema_slug", cinemaSlug)
                .put("room_number", roomNumber)
                .put("start_time", LocalDateTime.MIN)
                .put("end_time", endTime).toString();

        this.mockMvc.perform(get(endPoint + "/movies-in-room-for-period")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(jsonPath("$.success", is(false)))
                .andExpect(jsonPath("$.errors[0].errCode", is(422)))
                .andExpect(jsonPath("$.errors[0].errMsg", is("Start time can not be in the past")));
    }

    @Test
    public void findAllActualMoviesForARoomInTimeRangeWithInvalidSlug_ShouldThrow() throws Exception {

        String requestBody = new JSONObject()
                .put("cinema_slug", "invalidCinemaSlug")
                .put("room_number", roomNumber)
                .put("start_time", startTime)
                .put("end_time", endTime).toString();

        this.mockMvc.perform(get(endPoint + "/movies-in-room-for-period")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(jsonPath("$.success", is(false)))
                .andExpect(jsonPath("$.errors[0].errCode", is(422)))
                .andExpect(jsonPath("$.errors[0].errMsg", is("Cinema slug does not exist!")));
    }

    @Test
    public void findAllActualMoviesForARoomInTimeRangeWithInvalidRoomNumber_ShouldThrow() throws Exception {

        String requestBody = new JSONObject()
                .put("cinema_slug", cinemaSlug)
                .put("room_number", -1)
                .put("start_time", startTime)
                .put("end_time", endTime).toString();

        this.mockMvc.perform(get(endPoint + "/movies-in-room-for-period")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(jsonPath("$.success", is(false)))
                .andExpect(jsonPath("$.errors[0].errCode", is(422)))
                .andExpect(jsonPath("$.errors[0].errMsg", is("Room number can not be negative or zero")));
    }

    @Test
    public void findAllActualMoviesForARoomInTimeRangeWithNoActualRoom_ShouldThrow() throws Exception {

        String requestBody = new JSONObject()
                .put("cinema_slug", cinemaSlug)
                .put("room_number", 2)
                .put("start_time", LocalDateTime.now().plusYears(2))
                .put("end_time", LocalDateTime.now().plusYears(2).plusDays(2)).toString();

        this.mockMvc.perform(get(endPoint + "/movies-in-room-for-period")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(jsonPath("$.success", is(false)))
                .andExpect(jsonPath("$.errors[0].errCode", is(404)))
                .andExpect(jsonPath("$.errors[0].errMsg", is("Room is not found")));
    }

    private String parseTimeStampString(LocalDateTime localDateTime) {

        ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("UTC+2"));
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        return zonedDateTime.format(dateTimeFormatter);
    }
}