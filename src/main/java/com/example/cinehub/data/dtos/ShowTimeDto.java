package com.example.cinehub.data.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Data Transfer Object (DTO) for the {@link com.example.cinehub.data.entity.ShowTime} entity.
 * <p>
 * This class represents a simplified, serializable version of a ShowTime, designed to facilitate
 * the transfer of showtime-related data across different layers of the application, particularly
 * in API communications. It abstracts the internal details of the ShowTime entity and offers
 * a client-friendly data model.
 * <p>
 * The class includes validation annotations to ensure data integrity and correctness when
 * receiving data from or sending data to clients. It also maintains a relationship to the
 * {@link MovieDto}, indicating the movie associated with the showtime.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ShowTimeDto implements Serializable {

    /**
     * Unique identifier for the showtime.
     */
    private UUID id;

    /**
     * The start time of the movie show. This field is mandatory and is formatted as 'yyyy-MM-dd'.
     */
    @NotNull(message = "Start time can not be null")
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDateTime startTime;

    /**
     * The end time of the movie show. This field is mandatory and is formatted as 'yyyy-MM-dd'.
     */
    @NotNull(message = "End time can not be null")
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDateTime endTime;

    /**
     * The movie associated with this showtime. This field is mandatory but is ignored in JSON serialization to prevent circular dependence.
     */
    @NotNull
    @JsonIgnore
    private MovieDto movie;
}