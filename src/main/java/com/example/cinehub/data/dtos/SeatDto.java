package com.example.cinehub.data.dtos;

import lombok.*;
import org.hibernate.validator.constraints.Range;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.UUID;

/**
 * DTO for {@link com.example.cinehub.data.entity.Seat}
 */

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class SeatDto implements Serializable {
    private UUID id;

    @NotNull(message = "Row number can not be null")
    @Positive(message = "Row number can not be nagative")
    @Range(message = "Rows can not be more than 20", max = 20)
    private Integer rowNumber;

    @NotNull(message = "Seat number can not be null")
    @Positive(message = "Seat numbe can not be negative")
    @Range(message = "Seat number can not be more than 100", max = 100)
    private Integer seatNumber;

    @NotNull
    private RoomDto room;

}