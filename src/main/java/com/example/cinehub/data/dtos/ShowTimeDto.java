package com.example.cinehub.data.dtos;

import com.example.cinehub.data.entity.ShowTime;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO for {@link ShowTime}
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ShowTimeDto implements Serializable {
    private UUID id;
    @NotNull(message = "Start time can not be null")
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDateTime startTime;
    @NotNull(message = "End time can not be null")
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDateTime endTime;
    @NotNull
    @JsonIgnore
    private MovieDto movie;
}