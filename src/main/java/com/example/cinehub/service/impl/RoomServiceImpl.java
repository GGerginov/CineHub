package com.example.cinehub.service.impl;

import com.example.cinehub.data.dtos.RoomDto;
import com.example.cinehub.data.entity.Room;
import com.example.cinehub.exception.ApiException;
import com.example.cinehub.exception.jsonMessages.ErrorMessages;
import com.example.cinehub.repository.RoomRepository;
import com.example.cinehub.service.RoomService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Service implementation for room-related operations in the CineHub application.
 * <p>
 * This service provides methods for retrieving room information, including finding rooms by cinema slug,
 * finding all upcoming broadcasts, and finding rooms with movies in a specified time range.
 * It uses caching to improve performance for some methods.
 */
@Service
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;

    private final ModelMapper modelMapper;

    private final static Type LIST_OF_ROOM_DTO_TYPE =new TypeToken<List<RoomDto>>() {
    }.getType();

    /**
     * Constructs a RoomServiceImpl with the specified room repository and model mapper.
     *
     * @param roomRepository The repository for handling room-related data operations.
     * @param modelMapper    The model mapper for converting between DTOs and entities.
     */
    @Autowired
    public RoomServiceImpl(RoomRepository roomRepository, ModelMapper modelMapper) {
        this.roomRepository = roomRepository;
        this.modelMapper = modelMapper;
    }

    /**
     * Retrieves a list of rooms by a given cinema slug.
     * <p>
     * This method returns a list of RoomDto objects representing rooms found for the specified cinema slug.
     * The result is cached for improved performance.
     *
     * @param cinemaSlug The slug of the cinema for which to find rooms.
     * @return A list of RoomDto objects.
     * @throws ApiException if no rooms are found for the given cinema slug.
     */
    @Override
    @Cacheable(value = "roomsByCinemaSlug",key = "#cinemaSlug",unless = "#result.isEmpty()")
    public List<RoomDto> findRoomsByCinemaSlug(String cinemaSlug) throws ApiException {

        List<Room> roomsByCinemaSlug = this.roomRepository.findAllRoomsByCinemaSlug(cinemaSlug);

        if (roomsByCinemaSlug.isEmpty()){
            throw new ApiException(ErrorMessages.SLUG_NOT_FOUND);
        }

        return this.modelMapper.map(roomsByCinemaSlug,LIST_OF_ROOM_DTO_TYPE);
    }


    /**
     * Retrieves a list of all upcoming broadcasts in rooms.
     * <p>
     * This method returns a list of RoomDto objects representing rooms with upcoming broadcasts.
     * The result is cached based on the current hour for improved performance.
     *
     * @return A list of RoomDto objects.
     */
    @Override
    @Cacheable(value = "upcomingMoviesInRoom", key = "'allUpcomingBroadcasts:' + T(java.time.LocalDateTime).now().getHour()",unless = "#result.isEmpty()")
    public List<RoomDto> findAllUpcomingBroadcasts() {

        List<Room> roomList = this.roomRepository.findAllRoomsWithUpcomingBroadcasts();

        return this.modelMapper.map(roomList,LIST_OF_ROOM_DTO_TYPE);
    }

    /**
     * Finds a room with movies scheduled in a specified time range.
     * <p>
     * This method returns a RoomDto object representing a room with movies scheduled between the specified start and end times.
     *
     * @param roomNumber The number of the room.
     * @param cinemaSlug The slug of the cinema.
     * @param startTime  The start time of the range.
     * @param endTime    The end time of the range.
     * @return A RoomDto object.
     * @throws ApiException if no room is found matching the criteria.
     */
    @Override
    public RoomDto findRoomWithMoviesInRange(Integer roomNumber, String cinemaSlug, LocalDateTime startTime, LocalDateTime endTime) throws ApiException {

        Optional<Room> withMoviesInRange = this.roomRepository.findRoomWithMoviesInRange(roomNumber, cinemaSlug,
                startTime, endTime);

        if (withMoviesInRange.isPresent()){

            return this.modelMapper.map(withMoviesInRange,RoomDto.class);
        }
        else {
            throw new ApiException(ErrorMessages.ROOM_NOT_FOUND);
        }
    }
}
