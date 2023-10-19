package com.example.cinehub.data.dtos;

import com.example.cinehub.data.entity.Movie;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link Movie}
 */

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public final class MovieDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 0L;
    private String id;
    private String title;
    private String description;
    private LocalDateTime startTime;
    private RoomDto room;

}