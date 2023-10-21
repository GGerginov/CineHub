package com.example.cinehub.data.dtos;

import com.example.cinehub.data.entity.City;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.UUID;

/**
 * DTO for {@link City}
 */

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CityDto implements Serializable {

    private UUID id;

    @NotNull(message = "Name can not be null")
    @NotBlank(message = "Name can not be blank")
    private String name;
}