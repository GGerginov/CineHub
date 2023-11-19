package com.example.cinehub.data.dtos;

import lombok.*;
import org.hibernate.validator.constraints.Range;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.UUID;

/**
 * Data Transfer Object (DTO) for the {@link com.example.cinehub.data.entity.Seat} entity.
 * <p>
 * This class represents a simplified, serializable version of a Seat, designed to facilitate
 * the transfer of seat-related data across different layers of the application, particularly
 * in API communications. It abstracts the internal details of the Seat entity and offers
 * a client-friendly data model.
 * <p>
 * The class includes validation annotations to ensure data integrity and correctness when
 * receiving data from or sending data to clients. It also maintains a relationship to the
 * {@link RoomDto}, indicating the room in which the seat is located.
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class SeatDto implements Serializable {

    /**
     * Unique identifier for the seat.
     */
    private UUID id;

    /**
     * Row number of the seat in the room. This field is mandatory and must be a positive integer,
     * with a maximum limit of 20.
     */
    @NotNull(message = "Row number can not be null")
    @Positive(message = "Row number can not be nagative")
    @Range(message = "Rows can not be more than 20", max = 20)
    private Integer rowNumber;

    /**
     * Seat number in the row. This field is mandatory and must be a positive integer,
     * with a maximum limit of 100.
     */
    @NotNull(message = "Seat number can not be null")
    @Positive(message = "Seat numbe can not be negative")
    @Range(message = "Seat number can not be more than 100", max = 100)
    private Integer seatNumber;

    /**
     * The room to which the seat belongs. This field is mandatory.
     */
    @NotNull
    private RoomDto room;

}