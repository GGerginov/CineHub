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

@RestController
@RequestMapping("/api/cinemas")
public class CinemaController {

    private final CinemaService cinemaService;

    private final ModelMapper modelMapper;

    private final static Type LIST_OF_CINEMA_RESPONSE_DTO_TYPE = new TypeToken<List<CinemaResponseDTO>>() {
    }.getType();

    @Autowired
    public CinemaController(CinemaService cinemaService, ModelMapper modelMapper) {
        this.cinemaService = cinemaService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public ResponseEntity<?> getAllCinemas() {

        List<CinemaDto> allCinemas = cinemaService.findAllCinemas();

        List<CinemaResponseDTO> responseDTOList = this.modelMapper.map(allCinemas,LIST_OF_CINEMA_RESPONSE_DTO_TYPE);

        return new SuccessResponse<>(responseDTOList).getResponse();
    }

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
