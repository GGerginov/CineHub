package com.example.cinehub.service;

import com.example.cinehub.data.dtos.CinemaDto;
import com.example.cinehub.exception.ApiException;

import java.util.List;


public interface CinemaService {
    List<CinemaDto> findAllCinemas();

    List<CinemaDto> findCinemasByTownName(String townName) throws ApiException;
}
