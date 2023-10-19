package com.example.cinehub.data.dtos;

import com.example.cinehub.data.entity.Room;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

/**
 * DTO for {@link Room}
 */

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public final class RoomDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 0L;
    private String id;
    private Integer roomNumber;
    private Integer capacity;
    private CinemaDto cinema;


}