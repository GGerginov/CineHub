package com.example.cinehub.data.dtos;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

/**
 * DTO for {@link com.example.cinehub.data.entity.Cinema}
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CinemaDto implements Serializable {

    private UUID id;

    @NotNull(message = "Name can not be null")
    @NotBlank(message = "Name can not be blank")
    private String name;

    @NotNull(message = "Slug can not be null")
    @NotBlank(message = "Slug can not be blank")
    private String slug;

    @NotNull(message = "Address can not be null")
    private AddressDto address;

}