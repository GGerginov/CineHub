package com.example.cinehub.data.dtos;

import com.example.cinehub.data.entity.Cinema;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

/**
 * DTO for {@link Cinema}
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public final class CinemaDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 0L;
    private UUID id;
    private String name;
    private String address;
    private CityDto city;
    private String slug;


}