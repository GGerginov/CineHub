package com.example.cinehub.controller;

import com.example.cinehub.controller.requestDTOs.RoomMoviesRequestDTO;
import com.example.cinehub.controller.responseDTOs.RoomMovieResponseDTO;
import com.example.cinehub.controller.responseDTOs.RoomResponseDTO;
import com.example.cinehub.controller.responseDTOs.ShowTimeResponseDTO;
import com.example.cinehub.data.dtos.RoomDto;
import com.example.cinehub.exception.ApiException;
import com.example.cinehub.exception.jsonMessages.errorResponse.ErrorResponse;
import com.example.cinehub.exception.jsonMessages.successResponse.SuccessResponse;
import com.example.cinehub.service.RoomService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {

    private final RoomService roomService;
    private final ModelMapper modelMapper;

    @Autowired
    public RoomController(RoomService roomService,
                          ModelMapper modelMapper) {
        this.roomService = roomService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/{slug}")
    public ResponseEntity<?> getAllCinemas(@PathVariable @NotNull @NotBlank String slug) {

        try {
            List<RoomDto> roomsByCinemaSlug = this.roomService.findRoomsByCinemaSlug(slug);

            List<RoomResponseDTO> responseDTOList = roomsByCinemaSlug.stream()
                    .map(roomDto -> RoomResponseDTO.builder()
                            .roomNumber(roomDto.getRoomNumber())
                            .capacity(roomDto.getCapacity())
                            .cinemaSlug(roomDto.getCinema().getSlug())
                            .build())
                    .toList();

            return new SuccessResponse<>(responseDTOList).getResponse();
        } catch (ApiException e) {
            return new ErrorResponse(e).getResponse();
        }

    }

    @GetMapping("/upcoming-broadcasts")
    public ResponseEntity<?> listAllUpcomingBroadcasts(){

        List<RoomDto> allUpcomingBroadcasts = this.roomService.findAllUpcomingBroadcasts();

        List<RoomMovieResponseDTO> roomMovieResponseDTOS = allUpcomingBroadcasts.stream()
                .map(roomDto -> RoomMovieResponseDTO
                        .builder()
                        .roomNumber(roomDto.getRoomNumber())
                        .capacity(roomDto.getCapacity())
                        .cinemaSlug(roomDto.getCinema().getSlug())
                        .showTimes(roomDto.getShowTimes()
                                .stream()
                                .map(showTime -> ShowTimeResponseDTO.builder()
                                        .movieTitle(showTime.getMovie().getTitle())
                                        .movieDuration(showTime.getMovie().getDuration())
                                        .startTime(showTime.getStartTime())
                                        .endTime(showTime.getEndTime())
                                        .build())
                                .toList()
                        ).build())
                .toList();

        return new SuccessResponse<>(roomMovieResponseDTOS).getResponse();
    }


    @GetMapping("/movies-in-room-for-period")
    public ResponseEntity<?> findAllActualMoviesForARoomInTimeRange(@Validated @RequestBody RoomMoviesRequestDTO roomMoviesRequestDTO,
                                                                    Errors errors) {

        // TODO: 21.10.23 Validation not working
        if (errors.hasErrors()) {
            return new ErrorResponse(errors).getResponse();
        }

        RoomDto withMoviesInRange = this.roomService.findRoomWithMoviesInRange(roomMoviesRequestDTO.getRoomNumber()
                , roomMoviesRequestDTO.getCinemaSlug(),
                roomMoviesRequestDTO.getStart_time(),
                roomMoviesRequestDTO.getEnd_time());


        // TODO: 21.10.23 Try with model mapper
        RoomMovieResponseDTO responseDTO = RoomMovieResponseDTO.builder()
                .cinemaSlug(withMoviesInRange.getCinema().getSlug())
                .roomNumber(withMoviesInRange.getRoomNumber())
                .capacity(withMoviesInRange.getCapacity())
                .showTimes(withMoviesInRange.getShowTimes().stream()
                        .map(showTimeDto -> ShowTimeResponseDTO.builder()
                                .movieTitle(showTimeDto.getMovie().getTitle())
                                .movieDuration(showTimeDto.getMovie().getDuration())
                                .startTime(showTimeDto.getStartTime())
                                .endTime(showTimeDto.getEndTime())
                                .build())
                        .collect(Collectors.toList()))
                .build();

        return new SuccessResponse<>(responseDTO).getResponse();
    }
}
