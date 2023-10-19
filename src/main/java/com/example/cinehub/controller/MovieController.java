package com.example.cinehub.controller;

import com.example.cinehub.controller.responseDTOs.MovieResponseDTO;
import com.example.cinehub.controller.responseDTOs.RoomResponseDTO;
import com.example.cinehub.data.dtos.MovieDto;
import com.example.cinehub.exception.jsonMessages.successResponse.SuccessResponse;
import com.example.cinehub.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/movie")
public class MovieController {

    private final MovieService movieService;


    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllMoviesByRoom(){

        List<MovieDto> movieDtos = this.movieService.findAllActualMovies();


        List<MovieResponseDTO> responseDTOList = movieDtos.stream()
                .map(movieDto -> MovieResponseDTO
                        .builder()
                        .title(movieDto.getTitle())
                        .description(movieDto.getDescription())
                        .startTime(movieDto.getStartTime())
                        .roomResponseDTO(RoomResponseDTO
                                .builder()
                                .roomNumber(movieDto.getRoom().getRoomNumber())
                                .cinemaSlug(movieDto.getRoom().getCinema().getSlug())
                                .capacity(movieDto.getRoom().getCapacity())
                                .build())
                        .build())
                .toList();

        return new SuccessResponse<>(responseDTOList).getResponse();
    }
}
