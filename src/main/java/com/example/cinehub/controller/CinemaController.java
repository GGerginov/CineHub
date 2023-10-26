package com.example.cinehub.controller;

import com.example.cinehub.controller.responseDTOs.CinemaResponseDTO;
import com.example.cinehub.data.dtos.CinemaDto;
import com.example.cinehub.exception.ApiException;
import com.example.cinehub.exception.jsonMessages.errorResponse.ErrorResponse;
import com.example.cinehub.exception.jsonMessages.successResponse.SuccessResponse;
import com.example.cinehub.service.CinemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/cinemas")
public class CinemaController {

    private final CinemaService cinemaService;

    @Autowired
    public CinemaController(CinemaService cinemaService) {
        this.cinemaService = cinemaService;
    }

    @GetMapping
    public ResponseEntity<?> getAllCinemas() {

        List<CinemaDto> allCinemas = cinemaService.findAllCinemas();

        List<CinemaResponseDTO> responseDTOList = allCinemas.stream()
                .map(cinemaDto -> CinemaResponseDTO.builder()
                        .name(cinemaDto.getName())
                        .address(cinemaDto.getAddress())
                        .slug(cinemaDto.getSlug())
                        .cityName(cinemaDto.getCity().getName())
                        .build())
                .collect(Collectors.toList());

        return new SuccessResponse<>(responseDTOList).getResponse();
    }

    @GetMapping("/city/{cityName}")
    public ResponseEntity<?> getCinemasByCity(@PathVariable String cityName) {

        try {
            List<CinemaDto> cinemaByTownName = cinemaService.findCinemasByTownName(cityName);

            List<CinemaResponseDTO> responseDTOS = cinemaByTownName.stream()
                    .map(cinemaDto -> CinemaResponseDTO
                            .builder()
                            .name(cinemaDto.getName())
                            .cityName(cinemaDto.getCity().getName())
                            .address(cinemaDto.getAddress())
                            .slug(cinemaDto.getSlug())
                            .build())
                    .toList();

            return new SuccessResponse<>(responseDTOS).getResponse();
        } catch (ApiException e) {
            return new ErrorResponse(e).getResponse();
        }
    }

}
