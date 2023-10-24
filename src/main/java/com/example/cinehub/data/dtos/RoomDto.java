package com.example.cinehub.data.dtos;

import com.example.cinehub.data.entity.Room;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

/**
 * DTO for {@link Room}
 */

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class RoomDto implements Serializable {

    private UUID id;

    @NotNull(message = "Room number can not be null")
    @Positive(message = "Room number can not be negative")
    private Integer roomNumber;

    @NotNull(message = "Capacity can not be null")
    @Positive(message = "Capacity can not be negative")
    @Range(message = "Capacity can not be more than 150", max = 150)
    private Integer capacity;

    @NotNull
    private CinemaDto cinema;

    private List<ShowTimeDto> showTimes;
}