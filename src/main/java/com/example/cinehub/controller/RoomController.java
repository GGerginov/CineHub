package com.example.cinehub.controller;

import com.example.cinehub.controller.requestDTOs.RoomMoviesRequestDTO;
import com.example.cinehub.controller.responseDTOs.RoomMovieResponseDTO;
import com.example.cinehub.controller.responseDTOs.RoomResponseDTO;
import com.example.cinehub.data.dtos.RoomDto;
import com.example.cinehub.exception.ApiException;
import com.example.cinehub.exception.jsonMessages.errorResponse.ErrorResponse;
import com.example.cinehub.exception.jsonMessages.successResponse.SuccessResponse;
import com.example.cinehub.service.RoomService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Controller for handling room-related requests in the CineHub application.
 * <p>
 * This controller provides endpoints for retrieving room information,
 * including listing all rooms by cinema slug, listing all upcoming broadcasts,
 * and finding movies in a room for a specified time range.
 */
@RestController
@RequestMapping("/api/rooms")
public class RoomController {

    private final RoomService roomService;
    private final ModelMapper modelMapper;

    private final static Type LIST_OF_ROOM_RESPONSE_DTO_TYPE = new TypeToken<List<RoomResponseDTO>>() {
    }.getType();

    private final static Type LIST_OF_ROOM_MOVIE_RESPONSE_DTO_TYPE = new TypeToken<List<RoomMovieResponseDTO>>() {
    }.getType();


    /**
     * Constructs a RoomController with the specified room service and model mapper.
     *
     * @param roomService The service for handling room-related operations.
     * @param modelMapper The model mapper for converting between DTOs and entities.
     */
    @Autowired
    public RoomController(RoomService roomService, ModelMapper modelMapper) {
        this.roomService = roomService;
        this.modelMapper = modelMapper;
    }

    /**
     * Retrieves a list of all rooms associated with a given cinema slug.
     * <p>
     * This method returns a list of rooms in the form of RoomResponseDTOs.
     *
     * @param slug The slug of the cinema for which to find rooms.
     * @return A ResponseEntity containing a list of RoomResponseDTOs or an error response.
     */
    @GetMapping("/{slug}")
    public ResponseEntity<?> getAllRoomsByCinemaSlug(@PathVariable String slug) {

        try {
            List<RoomDto> roomsByCinemaSlug = this.roomService.findRoomsByCinemaSlug(slug);

            List<RoomResponseDTO> responseDTOList = this.modelMapper
                    .map(roomsByCinemaSlug, LIST_OF_ROOM_RESPONSE_DTO_TYPE);

            return new SuccessResponse<>(responseDTOList).getResponse();
        } catch (ApiException e) {
            return new ErrorResponse(e).getResponse();
        }
    }

    /**
     * Lists all upcoming broadcasts in all rooms.
     * <p>
     * This method returns a list of upcoming broadcasts across all rooms.
     *
     * @return A ResponseEntity containing a list of RoomMovieResponseDTOs or an error response.
     */
    @GetMapping("/upcoming-broadcasts")
    public ResponseEntity<?> listAllUpcomingBroadcasts() {

        List<RoomDto> allUpcomingBroadcasts = this.roomService.findAllUpcomingBroadcasts();

        List<RoomMovieResponseDTO> roomMovieResponseDTOS = this.modelMapper
                .map(allUpcomingBroadcasts, LIST_OF_ROOM_MOVIE_RESPONSE_DTO_TYPE);

        return new SuccessResponse<>(roomMovieResponseDTOS).getResponse();
    }


    /**
     * Finds all movies scheduled in a specific room within a given time range.
     * <p>
     * This method returns a list of movies scheduled in the specified room and time range.
     *
     * @param roomMoviesRequestDTO The DTO containing room number, cinema slug, and time range.
     * @param errors               Binding result containing validation errors.
     * @return A ResponseEntity containing RoomMovieResponseDTO or an error response.
     */
    @GetMapping("/movies-in-room-for-period")
    public ResponseEntity<?> findAllActualMoviesForARoomInTimeRange(@Validated @RequestBody RoomMoviesRequestDTO roomMoviesRequestDTO,
                                                                    Errors errors) {

        if (errors.hasErrors()) {
            return new ErrorResponse(errors).getResponse();
        }

        try {
            RoomDto withMoviesInRange = this.roomService.findRoomWithMoviesInRange(roomMoviesRequestDTO.roomNumber()
                    , roomMoviesRequestDTO.cinemaSlug(),
                    roomMoviesRequestDTO.start_time(),
                    roomMoviesRequestDTO.end_time());

            RoomMovieResponseDTO responseDTO = this.modelMapper.map(withMoviesInRange, RoomMovieResponseDTO.class);

            return new SuccessResponse<>(responseDTO).getResponse();
        } catch (ApiException e) {
            return new ErrorResponse(e).getResponse();
        }
    }
}
