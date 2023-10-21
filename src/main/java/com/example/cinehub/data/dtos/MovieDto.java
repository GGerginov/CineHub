package com.example.cinehub.data.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

/**
 * DTO for {@link com.example.cinehub.data.entity.Movie}
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MovieDto implements Serializable {

    private UUID id;

    @NotNull(message = "Title can not be null")
    @NotBlank(message = "Title can not be blank")
    private String title;

    private String description;

    @NotNull(message = "Duration can not be null")
    @Positive(message = "Duration can not be negative")
    private Integer duration;

    @NotNull
    private List<ShowTimeDto> showTimes;
}