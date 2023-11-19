package com.example.cinehub.data.dtos;

import com.example.cinehub.data.entity.Movie;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

/**
 * Data Transfer Object (DTO) for the {@link Movie} entity.
 * <p>
 * This class represents a simplified, serializable version of a Movie, designed to facilitate
 * the transfer of movie-related data across different layers of the application, particularly
 * in API communications. It abstracts the internal details of the Movie entity and offers
 * a client-friendly data model.
 * <p>
 * The class includes validation annotations to ensure data integrity and correctness when
 * receiving data from or sending data to clients.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MovieDto implements Serializable {

    /**
     * Unique identifier for the movie.
     */
    private UUID id;

    /**
     * Title of the movie. This field is mandatory and cannot be null or blank.
     */
    @NotNull(message = "Title can not be null")
    @NotBlank(message = "Title can not be blank")
    private String title;

    /**
     * Description of the movie. This field can be null and is used to provide additional
     * information about the movie.
     */
    private String description;

    /**
     * Duration of the movie in minutes. This field is mandatory and must be a positive integer,
     * indicating the length of the movie.
     */
    @NotNull(message = "Duration can not be null")
    @Positive(message = "Duration can not be negative")
    private Integer duration;

}