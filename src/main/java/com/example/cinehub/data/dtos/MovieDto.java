package com.example.cinehub.data.dtos;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link com.example.cinehub.data.entity.Movie}
 */
public record MovieDto(String id, String title, String description, LocalDateTime startTime,
                       RoomDto room) implements Serializable {
}