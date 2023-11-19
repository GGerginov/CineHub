package com.example.cinehub.data.entity;

import com.example.cinehub.data.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

/**
 * Represents a room within a cinema in the system.
 * <p>
 * This entity class is used to store information about individual rooms within a cinema. It extends
 * {@link BaseEntity} to inherit common identifier properties.
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class Room extends BaseEntity {

    /**
     * The number of the room.
     * This field is not nullable and is used to uniquely identify a room within a cinema.
     */
    @Column(nullable = false)
    private Integer roomNumber;

    /**
     * The seating capacity of the room.
     * This field is not nullable and stores the maximum number of people that the room can accommodate.
     */
    @Column(nullable = false)
    private Integer capacity;

    /**
     * The cinema to which the room belongs.
     * This field establishes a relationship with the {@link Cinema} entity,
     * indicating the cinema that houses this room.
     */
    @ManyToOne
    @JoinColumn(name = "cinema_id",nullable = false)
    private Cinema cinema;

    /**
     * A list of show times associated with the room.
     * This field establishes a relationship with the {@link ShowTime} entity,
     * indicating the various times when movies are scheduled to be shown in this room.
     */
    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.EAGER)
    @ToString.Exclude
    private List<ShowTime> showTimes ;

}
