package com.example.cinehub.service;

import com.example.cinehub.data.dtos.RoomDto;

import java.util.List;

public interface RoomService {

    List<RoomDto> findRoomsByCinemaSlug(String cinemaSlug);
}
