package com.example.cinehub.data.dtos;

import com.example.cinehub.data.entity.Room;

import java.io.Serializable;

/**
 * DTO for {@link Room}
 */
public record RoomDto(String id, Integer roomNumber, Integer capacity, CinemaDto cinema) implements Serializable {
}