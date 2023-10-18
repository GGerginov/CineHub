package com.example.cinehub.service;

import com.example.cinehub.data.dtos.CinemaDto;

import java.util.List;


public interface CinemaService {
    List<CinemaDto> findAllCinemas();
}
