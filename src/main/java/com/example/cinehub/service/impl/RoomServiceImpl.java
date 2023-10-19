package com.example.cinehub.service.impl;

import com.example.cinehub.data.dtos.RoomDto;
import com.example.cinehub.data.entity.Room;
import com.example.cinehub.repository.RoomRepository;
import com.example.cinehub.service.RoomService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;

@Service
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;

    private final ModelMapper modelMapper;

    @Autowired
    public RoomServiceImpl(RoomRepository roomRepository, ModelMapper modelMapper) {
        this.roomRepository = roomRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<RoomDto> findRoomsByCinemaSlug(String cinemaSlug) {

        List<Room> roomsByCinemaSlug = this.roomRepository.findAllRoomsByCinemaSlug(cinemaSlug);

        Type listOfCinemaDtoType = new TypeToken<List<RoomDto>>() {
        }.getType();

        return this.modelMapper.map(roomsByCinemaSlug,listOfCinemaDtoType);
    }
}
