package com.example.cinehub.service;

import com.example.cinehub.data.dtos.RoomDto;
import com.example.cinehub.exception.ApiException;

import java.time.LocalDateTime;
import java.util.List;

public interface RoomService {

    List<RoomDto> findRoomsByCinemaSlug(String cinemaSlug) throws ApiException;

    List<RoomDto> findAllUpcomingBroadcasts();

    RoomDto findRoomWithMoviesInRange(Integer roomNumber, String cinemaSlug, LocalDateTime startTime, LocalDateTime endTime);
}
