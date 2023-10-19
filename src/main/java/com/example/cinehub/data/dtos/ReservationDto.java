package com.example.cinehub.data.dtos;

import com.example.cinehub.data.entity.Reservation;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

/**
 * DTO for {@link Reservation}
 */

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public final class ReservationDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 0L;
    private String id;
    private SeatDto seat;
    private MovieDto movie;
    private Boolean isReserved;

}