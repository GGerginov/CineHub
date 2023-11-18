package com.example.cinehub.controller;

import com.example.cinehub.controller.responseDTOs.CinemaResponseDTO;
import com.example.cinehub.data.dtos.CinemaDto;
import com.example.cinehub.exception.ApiException;
import com.example.cinehub.exception.jsonMessages.errorResponse.ErrorResponse;
import com.example.cinehub.exception.jsonMessages.successResponse.SuccessResponse;
import com.example.cinehub.service.CinemaService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Controller for handling cinema-related requests in the CineHub application.
 * <p>
 * This controller provides endpoints for retrieving cinema information,
 * including listing all cinemas and fetching cinemas based on city names.
 */
@RestController
@RequestMapping("/api/cinemas")
public class CinemaController {

    private final CinemaService cinemaService;

    private final ModelMapper modelMapper;

    private final static Type LIST_OF_CINEMA_RESPONSE_DTO_TYPE = new TypeToken<List<CinemaResponseDTO>>() {
    }.getType();

    /**
     * Constructs a CinemaController with the specified cinema service and model mapper.
     *
     * @param cinemaService The service for handling cinema-related operations.
     * @param modelMapper   The model mapper for converting between DTOs and entities.
     */
    @Autowired
    public CinemaController(CinemaService cinemaService, ModelMapper modelMapper) {
        this.cinemaService = cinemaService;
        this.modelMapper = modelMapper;
    }

    /**
     * Retrieves a list of all cinemas.
     * <p>
     * This method returns a list of all cinemas in the form of CinemaResponseDTOs.
     *
     * @return A ResponseEntity containing a list of CinemaResponseDTOs or an error response.
     */
    @GetMapping
    public ResponseEntity<?> getAllCinemas() {

        List<CinemaDto> allCinemas = cinemaService.findAllCinemas();

        List<CinemaResponseDTO> responseDTOList = this.modelMapper.map(allCinemas,LIST_OF_CINEMA_RESPONSE_DTO_TYPE);

        return new SuccessResponse<>(responseDTOList).getResponse();
    }

    /**
     * Retrieves a list of cinemas based on the provided city name.
     * <p>
     * This method returns a list of cinemas located in the specified city.
     *
     * @param cityName The name of the city for which to find cinemas.
     * @return A ResponseEntity containing a list of CinemaResponseDTOs for the specified city or an error response.
     */
    @GetMapping("/city/{cityName}")
    public ResponseEntity<?> getCinemasByCity(@PathVariable String cityName) {

        try {
            List<CinemaDto> cinemaByTownName = cinemaService.findCinemasByTownName(cityName);

            List<CinemaResponseDTO> responseDTOList = this.modelMapper
                    .map(cinemaByTownName,LIST_OF_CINEMA_RESPONSE_DTO_TYPE);

            return new SuccessResponse<>(responseDTOList).getResponse();
        } catch (ApiException e) {
            return new ErrorResponse(e).getResponse();
        }
    }

}
