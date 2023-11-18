package com.example.cinehub.data.dtos;

import com.example.cinehub.data.entity.Room;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

/**
 * Data Transfer Object (DTO) for the {@link Room} entity.
 * <p>
 * This class represents a simplified, serializable version of a Room, designed to facilitate
 * the transfer of room-related data across different layers of the application, particularly
 * in API communications. It abstracts the internal details of the Room entity and offers
 * a client-friendly data model.
 * <p>
 * The class includes validation annotations to ensure data integrity and correctness when
 * receiving data from or sending data to clients. It also includes relationships to other
 * relevant DTOs like {@link CinemaDto} and {@link ShowTimeDto}.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class RoomDto implements Serializable {

    /**
     * Unique identifier for the room.
     */
    private UUID id;

    /**
     * Number of the room. This field is mandatory and must be a positive integer.
     */
    @NotNull(message = "Room number can not be null")
    @Positive(message = "Room number can not be negative")
    private Integer roomNumber;

    /**
     * Capacity of the room. This field is mandatory and must be a positive integer,
     * with a maximum limit of 150.
     */
    @NotNull(message = "Capacity can not be null")
    @Positive(message = "Capacity can not be negative")
    @Range(message = "Capacity can not be more than 150", max = 150)
    private Integer capacity;

    /**
     * The cinema to which the room belongs. This field is mandatory.
     */
    @NotNull
    private CinemaDto cinema;

    /**
     * List of showtimes associated with the room. This field can be null if no showtimes
     * are scheduled for the room.
     */
    private List<ShowTimeDto> showTimes;
}