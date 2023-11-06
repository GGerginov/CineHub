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

@Service
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;

    private final ModelMapper modelMapper;

    private final static Type LIST_OF_ROOM_DTO_TYPE =new TypeToken<List<RoomDto>>() {
    }.getType();

    @Autowired
    public RoomServiceImpl(RoomRepository roomRepository, ModelMapper modelMapper) {
        this.roomRepository = roomRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Cacheable(value = "roomsByCinemaSlug",key = "#cinemaSlug",unless = "#result.isEmpty()")
    public List<RoomDto> findRoomsByCinemaSlug(String cinemaSlug) throws ApiException {

        List<Room> roomsByCinemaSlug = this.roomRepository.findAllRoomsByCinemaSlug(cinemaSlug);

        if (roomsByCinemaSlug.isEmpty()){
            throw new ApiException(ErrorMessages.SLUG_NOT_FOUND);
        }

        return this.modelMapper.map(roomsByCinemaSlug,LIST_OF_ROOM_DTO_TYPE);
    }

    @Override
    @Cacheable(value = "upcomingMoviesInRoom", key = "'allUpcomingBroadcasts:' + T(java.time.LocalDateTime).now().getHour()",unless = "#result.isEmpty()")
    public List<RoomDto> findAllUpcomingBroadcasts() {

        List<Room> roomList = this.roomRepository.findAllRoomsWithUpcomingBroadcasts();

        return this.modelMapper.map(roomList,LIST_OF_ROOM_DTO_TYPE);
    }

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
