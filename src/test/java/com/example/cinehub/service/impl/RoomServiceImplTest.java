package com.example.cinehub.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import com.example.cinehub.data.dtos.CinemaDto;
import com.example.cinehub.data.dtos.AddressDto;
import com.example.cinehub.data.dtos.RoomDto;
import com.example.cinehub.data.entity.Address;
import com.example.cinehub.data.entity.Cinema;
import com.example.cinehub.data.entity.Room;
import com.example.cinehub.exception.ApiException;
import com.example.cinehub.exception.jsonMessages.ErrorMessage;
import com.example.cinehub.repository.RoomRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.*;

@ExtendWith(MockitoExtension.class)
public class RoomServiceImplTest {

    @Mock
    private RoomRepository roomRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private RoomServiceImpl roomService;

    private String invalidSlug ;

    private final List<Room> expectedRooms = new ArrayList<>();
    private final List<RoomDto> expectedRoomDtoS = new ArrayList<>();

    @BeforeEach
    void setUp() {
        invalidSlug= "invalid-slug";

        Cinema cinema = new Cinema();
        cinema.setId(UUID.randomUUID());
        cinema.setName("Demo cinema");
        cinema.setSlug("demo-cinema-1");
        cinema.setAddress(new Address());

        CinemaDto cinemaDto = new CinemaDto(cinema.getId(),cinema.getName(),cinema.getSlug(), new AddressDto());

        Room room1 = new Room();
        room1.setId(UUID.randomUUID());
        room1.setRoomNumber(1);
        room1.setCapacity(30);
        room1.setCinema(cinema);

        Room room2 = new Room();
        room2.setId(UUID.randomUUID());
        room2.setRoomNumber(2);
        room2.setCapacity(30);
        room2.setCinema(cinema);

        Room room3 = new Room();
        room3.setId(UUID.randomUUID());
        room3.setRoomNumber(2);
        room3.setCapacity(30);
        room3.setCinema(cinema);

        this.expectedRooms.addAll(List.of(room1,room2,room3));

        RoomDto room1Dto = new RoomDto(room1.getId(),room1.getRoomNumber(),
                room1.getCapacity(),
                cinemaDto,Collections.emptyList());

        RoomDto room2Dto = new RoomDto(room2.getId(),room2.getRoomNumber(),
                room2.getCapacity(),
                cinemaDto,Collections.emptyList());

        RoomDto room3Dto = new RoomDto(room3.getId(),room3.getRoomNumber(),
                room3.getCapacity(),
                cinemaDto,Collections.emptyList());

        this.expectedRoomDtoS.addAll(List.of(room1Dto,room2Dto,room3Dto));

        lenient().when(modelMapper.map(any(), any(Type.class))).thenReturn(expectedRoomDtoS);
    }

    @Test
    public void whenFindRoomsByCinemaSlugWithValidSlug_thenRoomsShouldBeReturned() throws ApiException {

        when(roomRepository.findAllRoomsByCinemaSlug("validSlug")).thenReturn(expectedRooms);

        List<RoomDto> actualRoomDtos = roomService.findRoomsByCinemaSlug("validSlug");

        assertFalse(actualRoomDtos.isEmpty());
        assertEquals(expectedRoomDtoS.size(), actualRoomDtos.size());

        for (int i = 0; i < actualRoomDtos.size(); i++) {

            RoomDto roomDto = actualRoomDtos.get(i);

            assertEquals(expectedRoomDtoS.get(i).getId(),roomDto.getId());
            assertEquals(expectedRoomDtoS.get(i).getRoomNumber(),roomDto.getRoomNumber());
            assertEquals(expectedRoomDtoS.get(i).getCapacity(),roomDto.getCapacity());
            assertEquals(expectedRoomDtoS.get(i).getCinema().getId(),roomDto.getCinema().getId());
            assertEquals(expectedRoomDtoS.get(i).getCinema().getName(),roomDto.getCinema().getName());
            assertEquals(expectedRoomDtoS.get(i).getCinema().getAddress().getCityName(),roomDto.getCinema().getAddress().getCityName());
        }

    }

    @Test
    public void whenFindRoomsByCinemaSlugWithInvalidSlug_thenExceptionShouldBeThrown() {
        when(roomRepository.findAllRoomsByCinemaSlug(invalidSlug)).thenReturn(Collections.emptyList());

        ErrorMessage errorMessage = assertThrows(ApiException.class, () -> roomService.findRoomsByCinemaSlug(invalidSlug)).getErrorMessage();

        assertEquals(errorMessage.getErrCode(), 404);
        assertEquals(errorMessage.getErrMsg(), "Slug is not found");
    }

    @Test
    public void whenFindAllUpcomingBroadcasts_thenRoomsShouldBeReturned(){

        when(roomRepository.findAllRoomsWithUpcomingBroadcasts())
                .thenReturn(expectedRooms);

        List<RoomDto> actualRoomDtos = this.roomService.findAllUpcomingBroadcasts();


        assertFalse(actualRoomDtos.isEmpty());
        assertEquals(expectedRoomDtoS.size(), actualRoomDtos.size());

        for (int i = 0; i < actualRoomDtos.size(); i++) {

            RoomDto roomDto = actualRoomDtos.get(i);

            assertEquals(expectedRoomDtoS.get(i).getId(),roomDto.getId());
            assertEquals(expectedRoomDtoS.get(i).getRoomNumber(),roomDto.getRoomNumber());
            assertEquals(expectedRoomDtoS.get(i).getCapacity(),roomDto.getCapacity());
            assertEquals(expectedRoomDtoS.get(i).getCinema().getId(),roomDto.getCinema().getId());
            assertEquals(expectedRoomDtoS.get(i).getCinema().getName(),roomDto.getCinema().getName());
            assertEquals(expectedRoomDtoS.get(i).getCinema().getAddress().getCityName(),roomDto.getCinema().getAddress().getCityName());
        }


    }

    @Test
    public void whenFindRoomWithMoviesInRange_thenRoomsShouldBeReturned() throws ApiException {

        Room expectedRoom = expectedRooms.get(0);
        when(roomRepository.findRoomWithMoviesInRange(1,"demo-cinema-1", LocalDateTime.MIN,LocalDateTime.MAX))
                .thenReturn(Optional.of(expectedRoom));

        when(modelMapper.map(Optional.of(expectedRoom),RoomDto.class)).thenReturn(expectedRoomDtoS.get(0));

        RoomDto actualRoomDto = this.roomService.findRoomWithMoviesInRange(1, "demo-cinema-1", LocalDateTime.MIN, LocalDateTime.MAX);

        assertEquals(expectedRoom.getId(),actualRoomDto.getId());
        assertEquals(expectedRoom.getRoomNumber(),actualRoomDto.getRoomNumber());
        assertEquals(expectedRoom.getCapacity(),actualRoomDto.getCapacity());
        assertEquals(expectedRoom.getCinema().getId(),actualRoomDto.getCinema().getId());
        assertEquals(expectedRoom.getCinema().getName(),actualRoomDto.getCinema().getName());
        assertEquals(expectedRoom.getCinema().getAddress().getCityName(),actualRoomDto.getCinema().getAddress().getCityName());
    }

    @Test
    public void whenFindRoomWithMoviesInRangeWithInvalidParams_thenExceptionShouldBeThrown() {
        when(roomRepository.findRoomWithMoviesInRange(1,"demo-cinema-1", LocalDateTime.MIN,LocalDateTime.MAX))
                .thenReturn(Optional.empty());

        ErrorMessage errorMessage = assertThrows(ApiException.class, () -> this.roomService.findRoomWithMoviesInRange(1, "demo-cinema-1", LocalDateTime.MIN, LocalDateTime.MAX)).getErrorMessage();

        assertEquals(errorMessage.getErrCode(), 404);
        assertEquals(errorMessage.getErrMsg(), "Room is not found");
    }

}
