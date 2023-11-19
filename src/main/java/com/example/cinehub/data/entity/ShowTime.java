package com.example.cinehub.data.entity;

import com.example.cinehub.data.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a specific showtime for a movie in a particular room within the cinema system.
 * <p>
 * This entity class is used to store information about a particular screening of a movie, including its start and end times.
 * It extends {@link BaseEntity} to inherit common identifier properties.
 * A unique constraint is applied to ensure that no two showt imes overlap in the same room.
 * </p>
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@Table(name = "show_time",
        uniqueConstraints = @UniqueConstraint(columnNames = {"room_id", "start_time"}))
public class ShowTime extends BaseEntity {

    /**
     * The start time of the show.
     * This field is not nullable and indicates when the movie screening begins.
     */
    @Column(name = "start_time",nullable = false)
    private LocalDateTime startTime;

    /**
     * The end time of the show.
     * This field is not nullable and indicates when the movie screening ends.
     */
    @Column(name = "end_time",nullable = false)
    private LocalDateTime endTime;

    /**
     * The movie being shown.
     * This field establishes a many-to-one relationship with the {@link Movie} entity,
     * indicating the movie that is being screened during this showtime.
     */
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "movie_id",nullable = false)
    private Movie movie;

    /**
     * The room in which the show is being screened.
     * This field establishes a many-to-one relationship with the {@link Room} entity,
     * indicating the room where this showtime takes place.
     */
    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    /**
     * The movie being shown.
     * This field establishes a relationship with the {@link Movie} entity,
     * indicating the movie that is being screened during this showtime.
     */
    @OneToMany(mappedBy = "showTime", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<Ticket> tickets = new ArrayList<>();

}