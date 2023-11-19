package com.example.cinehub.service.impl;

import com.example.cinehub.data.dtos.RoomDto;
import com.example.cinehub.data.entity.Room;
import com.example.cinehub.exception.ApiException;
import com.example.cinehub.exception.jsonMessages.ErrorMessage;
import com.example.cinehub.repository.CinemaRepository;
import com.example.cinehub.repository.RoomRepository;
import com.example.cinehub.service.RoomService;
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

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest
@Testcontainers
class RoomServiceIntegrationTest {

    @Autowired
    private CinemaRepository cinemaRepository;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private RoomService roomService;

    @Container
    public static GenericContainer<?> redis = new GenericContainer<>(DockerImageName.parse("redis:latest"))
            .withExposedPorts(6379)
            .waitingFor(Wait.forListeningPort());

    @DynamicPropertySource
    static void redisProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.redis.host", redis::getHost);
        registry.add("spring.data.redis.port", () -> redis.getMappedPort(6379).toString());
    }

    @Test
    void whenFindRoomsByCinemaSlug_thenRoomsShouldBeFound() {
        String slug = this.cinemaRepository.findAll().get(0).getSlug();

        List<Room> expected = this.roomRepository.findAllRoomsByCinemaSlug(slug);

        List<Room> actual = this.roomRepository.findAllRoomsByCinemaSlug(slug);

        assertEquals(expected.size(),actual.size());

        for (int i = 0; i < actual.size(); i++) {

            assertEquals(expected.get(i).getId(),actual.get(i).getId());
            assertEquals(expected.get(i).getRoomNumber(),actual.get(i).getRoomNumber());
            assertEquals(expected.get(i).getCapacity(),actual.get(i).getCapacity());
        }
    }

    @Test
    void whenFindRoomsByInvalidCinemaSlug_thenShouldTrow() {

        ErrorMessage errorMessage = assertThrows(ApiException.class, () -> this.roomService.findRoomsByCinemaSlug("invalid")).getErrorMessage();

        assertEquals(404,errorMessage.getErrCode());
        assertEquals("Slug is not found",errorMessage.getErrMsg());
    }

    @Test
    void whenFindAllUpcomingBroadcasts_thenShouldBeFound() {

        List<Room> expected = this.roomRepository.findAllRoomsWithUpcomingBroadcasts();

        List<RoomDto> actual = this.roomService.findAllUpcomingBroadcasts();

        for (int i = 0; i < actual.size(); i++) {

            assertEquals(expected.get(i).getId(),actual.get(i).getId());
            assertEquals(expected.get(i).getRoomNumber(),actual.get(i).getRoomNumber());
            assertEquals(expected.get(i).getCapacity(),actual.get(i).getCapacity());
        }

    }
}