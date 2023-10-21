package com.example.cinehub.data.dtos;

import com.example.cinehub.data.entity.ShowTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
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
    private LocalDateTime startTime;
    @NotNull(message = "End time can not be null")
    private LocalDateTime endTime;
    @NotNull
    private MovieDto movie;
    @NotNull
    private List<TicketDto> tickets;
}