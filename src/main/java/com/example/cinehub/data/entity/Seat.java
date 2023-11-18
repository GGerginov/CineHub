package com.example.cinehub.data.entity;

import com.example.cinehub.data.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;


/**
 * Represents a seat within a room in the cinema system.
 * <p>
 * This entity class is used to store information about individual seats within a room of a cinema. It extends
 * {@link BaseEntity} to inherit common identifier properties.
 * A unique constraint is applied to ensure that there is no two seats that have the same row and seat number within the same room.
 * </p>
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@Table(
        name = "seat",
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"row_number", "seat_number", "room_id"}
        )
)
public class Seat extends BaseEntity {

    /**
     * The row number of the seat.
     * This field is not nullable and is used to identify the row in which the seat is located.
     */
    @Column(nullable = false,name = "row_number")
    private Integer rowNumber;

    /**
     * The seat number.
     * This field is not nullable and is used to identify the seat within its row.
     */
    @Column(nullable = false,name = "seat_number")
    private Integer seatNumber;

    /**
     * The room to which the seat belongs.
     * This field establishes a relationship with the {@link Room} entity,
     * indicating the room that houses this seat.
     */
    @ManyToOne
    @JoinColumn(name = "room_id",nullable = false)
    private Room room;

}
