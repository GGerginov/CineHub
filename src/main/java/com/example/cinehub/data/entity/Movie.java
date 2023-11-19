package com.example.cinehub.data.entity;

import com.example.cinehub.data.base.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.List;

/**
 * Represents a movie in the application.
 * <p>
 * The Movie class is an entity class that extends the {@link BaseEntity} class. It contains information about a movie,
 * such as its title, description, duration, and show times.
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class Movie extends BaseEntity {


    /**
     * The title of the movie.
     * This field is not nullable and is used to store the movie's title.
     */
    @Column(nullable = false)
    private String title;

    /**
     * A brief description of the movie.
     * This field is optional and is used to store a description of the movie.
     */
    private String description;

    /**
     * The duration of the movie in minutes.
     * This field is not nullable and stores the length of the movie.
     */
    @Column(nullable = false)
    private Integer duration;

    /**
     * A list of show times associated with the movie.
     * This field establishes a relationship with the {@link ShowTime} entity,
     * indicating the various show times when the movie is scheduled to be shown.
     */
    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<ShowTime> showTimes;

}
