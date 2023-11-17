package com.example.cinehub.controller;

import com.example.cinehub.data.entity.Ticket;
import com.example.cinehub.repository.TicketRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.is;
import static org.hibernate.validator.internal.util.Contracts.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Testcontainers
@AutoConfigureWebTestClient
class TicketControllerTest {

    @Autowired
    WebApplicationContext wac;

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private TicketRepository ticketRepository;

    private MockMvc mockMvc;
    private String endPoint;

    private String slug;

    private String movieTitle;

    private Integer roomNumber;

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
        this.endPoint = "/api/ticket/";
        this.roomNumber = 1;
        this.movieTitle = "Adventure Land";
        this.slug = "sofia-cinema-1";
        redis.start();
    }

    @Test
    void testGetAllTicketByCinemaSlugRoomNumberAndMovieTitle_shouldReturn() throws Exception {

        String requestBody = new JSONObject()
                .put("slug", this.slug)
                .put("room_number", this.roomNumber)
                .put("movie_title", this.movieTitle).toString();

        ResultActions resultActions = this.mockMvc.perform(get(endPoint + "/check")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success", is(true)));

        List<Ticket> tickets = this.ticketRepository.findAllTicketsByRoomNumberAndSlugAndMovieTitle(this.roomNumber, this.slug, this.movieTitle);

        for (int i = 0; i < tickets.size(); i++) {

            Ticket current = tickets.get(i);
            resultActions
                    .andExpect(jsonPath("$.data" + "[" + i + "]" + ".id", is(current.getId().toString())))
                    .andExpect(jsonPath("$.data" + "[" + i + "]" + ".ticket_type", is(current.getTicketType().toString())))
                    .andExpect(jsonPath("$.data" + "[" + i + "]" + ".ticket_price", is(current.getPrice())))
                    .andExpect(jsonPath("$.data" + "[" + i + "]" + ".show_time_start", is(this.parseTimeStampString(current.getShowTime().getStartTime()))))
                    .andExpect(jsonPath("$.data" + "[" + i + "]" + ".show_time_end", is(this.parseTimeStampString(current.getShowTime().getEndTime()))))
                    .andExpect(jsonPath("$.data" + "[" + i + "]" + ".movie_title", is(current.getShowTime().getMovie().getTitle())))
                    .andExpect(jsonPath("$.data" + "[" + i + "]" + ".is_reserved", is(current.getIsReserved())))
                    .andExpect(jsonPath("$.data" + "[" + i + "]" + ".seat_row", is(current.getSeat().getRowNumber())))
                    .andExpect(jsonPath("$.data" + "[" + i + "]" + ".seat_number", is(current.getSeat().getSeatNumber())))
                    .andExpect(jsonPath("$.data" + "[" + i + "]" + ".room_number", is(current.getSeat().getRoom().getRoomNumber())))
                    .andExpect(jsonPath("$.data" + "[" + i + "]" + ".cinema_name", is(current.getSeat().getRoom().getCinema().getName())))
                    .andExpect(jsonPath("$.data" + "[" + i + "]" + ".cinema_city", is(current.getSeat().getRoom().getCinema().getAddress().getCityName())))
                    .andExpect(jsonPath("$.data" + "[" + i + "]" + ".cinema_street", is(current.getSeat().getRoom().getCinema().getAddress().getStreet())))
                    .andExpect(jsonPath("$.data" + "[" + i + "]" + ".cinema_slug", is(current.getSeat().getRoom().getCinema().getSlug())));
        }

    }


    @Test
    public void testGetAllTicketByCinemaSlugRoomNumberAndMovieTitleByInvalidSlug_shouldThrow() throws Exception {

        String requestBody = new JSONObject()
                .put("slug", "invalid slug")
                .put("room_number", this.roomNumber)
                .put("movie_title", this.movieTitle).toString();

        this.mockMvc.perform(get(endPoint + "/check")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success", is(false)))
                .andExpect(jsonPath("$.errors[0].errCode", is(422)))
                .andExpect(jsonPath("$.errors[0].errMsg", is("Cinema slug does not exist!")));
    }

    @Test
    public void testGetAllTicketByCinemaSlugRoomNumberAndMovieTitleByInvalidTitle_shouldThrow() throws Exception {

        String requestBody = new JSONObject()
                .put("slug", this.slug)
                .put("room_number", this.roomNumber)
                .put("movie_title", "invalidTitle").toString();

        this.mockMvc.perform(get(endPoint + "/check")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success", is(false)))
                .andExpect(jsonPath("$.errors[0].errCode", is(422)))
                .andExpect(jsonPath("$.errors[0].errMsg", is("Movie title does not exist!")));
    }

    @Test
    public void testGetAllTicketByCinemaSlugRoomNumberAndMovieTitleByInvalidRoomNumber_shouldThrow() throws Exception {

        String requestBody = new JSONObject()
                .put("slug", this.slug)
                .put("room_number", 0)
                .put("movie_title", this.movieTitle).toString();

        this.mockMvc.perform(get(endPoint + "/check")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success", is(false)))
                .andExpect(jsonPath("$.errors[0].errCode", is(422)))
                .andExpect(jsonPath("$.errors[0].errMsg", is("Room number can not be negative or zero")));
    }


    @Test
    void testBookTicketById_ShouldBookTheTicket() {

        Ticket ticketToBook = this.ticketRepository.findAll().get(0);

        webTestClient.put().uri("/api/ticket/book/" + ticketToBook.getId().toString())
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.success").isEqualTo(true)
                .jsonPath("$.data.id").isEqualTo(ticketToBook.getId().toString())
                .jsonPath("$.data.ticket_type").isEqualTo(ticketToBook.getTicketType().toString())
                .jsonPath("$.data.ticket_price").isEqualTo(ticketToBook.getPrice())
                .jsonPath("$.data.show_time_start").isEqualTo(this.parseTimeStampString(ticketToBook.getShowTime().getStartTime()))
                .jsonPath("$.data.show_time_end").isEqualTo(this.parseTimeStampString(ticketToBook.getShowTime().getEndTime()))
                .jsonPath("$.data.movie_title").isEqualTo(ticketToBook.getShowTime().getMovie().getTitle())
                .jsonPath("$.data.is_reserved").isEqualTo(true)
                .jsonPath("$.data.seat_row").isEqualTo(ticketToBook.getSeat().getRowNumber())
                .jsonPath("$.data.seat_number").isEqualTo(ticketToBook.getSeat().getSeatNumber())
                .jsonPath("$.data.room_number").isEqualTo(ticketToBook.getSeat().getRoom().getRoomNumber())
                .jsonPath("$.data.cinema_name").isEqualTo(ticketToBook.getSeat().getRoom().getCinema().getName())
                .jsonPath("$.data.cinema_city").isEqualTo(ticketToBook.getSeat().getRoom().getCinema().getAddress().getCityName())
                .jsonPath("$.data.cinema_street").isEqualTo(ticketToBook.getSeat().getRoom().getCinema().getAddress().getStreet())
                .jsonPath("$.data.cinema_street").isEqualTo(ticketToBook.getSeat().getRoom().getCinema().getAddress().getStreet())
                .jsonPath("$.data.cinema_slug").isEqualTo(ticketToBook.getSeat().getRoom().getCinema().getSlug());
    }

    @Test
    void testBookTicketByInvalidId_ShouldThrow() {

        webTestClient.put().uri("/api/ticket/book/" + "invalidId")
                .exchange()
                .expectStatus().is4xxClientError()
                .expectBody()
                .jsonPath("$.success").isEqualTo(false)
                .jsonPath("$.errors[0].errCode").isEqualTo(422)
                .jsonPath("$.errors[0].errMsg").isEqualTo("Ticket id is not valid");
    }

    @Test
    void testBookTicketByNotActualId_ShouldThrow() {

        webTestClient.put().uri("/api/ticket/book/" + UUID.randomUUID())
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("$.success").isEqualTo(false)
                .jsonPath("$.errors[0].errCode").isEqualTo(404)
                .jsonPath("$.errors[0].errMsg").isEqualTo("Ticket is not found");
    }

    @Test
    void testBookTicketByWhenTicketIsAlreadyBooked_ShouldThrow() {

        Ticket ticket = this.ticketRepository.findAll().get(1);

        ticket.setIsReserved(true);

        this.ticketRepository.saveAndFlush(ticket);

        webTestClient.put().uri("/api/ticket/book/" + ticket.getId())
                .exchange()
                .expectStatus().is4xxClientError()
                .expectBody()
                .jsonPath("$.success").isEqualTo(false)
                .jsonPath("$.errors[0].errCode").isEqualTo(409)
                .jsonPath("$.errors[0].errMsg").isEqualTo("Ticket is already booked");
    }

    @Test
    public void testTicketBookingRaceCondition() {

        Ticket ticket = this.ticketRepository.findAll().get(0);
        ticket.setIsReserved(false);
        this.ticketRepository.save(ticket);

        CompletableFuture<Boolean> request1 = CompletableFuture.supplyAsync(() ->
                bookTicket(ticket.getId()));

        CompletableFuture<Boolean> request2 = CompletableFuture.supplyAsync(() ->
                bookTicket(ticket.getId()));

        CompletableFuture.allOf(request1, request2).join();

        boolean atLeastOneFailed = Stream.of(request1, request2)
                .anyMatch(future -> {
                    try {
                        return !future.get();
                    } catch (InterruptedException | ExecutionException e) {
                        throw new RuntimeException(e);
                    }
                });

        boolean atLeastOneSucceed = Stream.of(request1, request2)
                .anyMatch(future -> {
                    try {
                        return future.get();
                    } catch (InterruptedException | ExecutionException e) {
                        throw new RuntimeException(e);
                    }
                });

        assertTrue(atLeastOneFailed, "At least one request should fail due to double booking");
        assertTrue(atLeastOneSucceed, "At least one request should succeed due to double booking");

        Ticket updatedTicket = this.ticketRepository.findById(ticket.getId()).orElse(null);

        assertNotNull(updatedTicket, "Ticket should exist");
        assertTrue(updatedTicket.getIsReserved(), "Ticket should be booked after concurrent requests");
    }

    private Boolean bookTicket(UUID ticketId) {
        return webTestClient.put().uri("/api/ticket/book/" + ticketId)
                .exchange()
                .returnResult(String.class)
                .getResponseBody()
                .map(this::stringToJson)
                .map(jsonNode -> jsonNode.get("success").asBoolean()).blockFirst();
    }
    private String parseTimeStampString(LocalDateTime localDateTime) {

        ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("UTC+2"));
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return zonedDateTime.format(dateTimeFormatter);
    }

    private JsonNode stringToJson(String jsonString) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readTree(jsonString);
        } catch (Exception e) {
            return null;
        }
    }
}