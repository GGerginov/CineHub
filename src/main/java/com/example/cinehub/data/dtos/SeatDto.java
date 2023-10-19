package com.example.cinehub.data.dtos;

import com.example.cinehub.data.entity.Seat;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

/**
 * DTO for {@link Seat}
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public final class SeatDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 0L;
    private String id;
    private Integer rowNumber;
    private Integer seatNumber;
    private RoomDto room;

}