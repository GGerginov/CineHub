package com.example.cinehub.data.dtos;

import com.example.cinehub.data.entity.City;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

/**
 * DTO for {@link City}
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public final class CityDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 0L;
    private String id;
    private String name;

}