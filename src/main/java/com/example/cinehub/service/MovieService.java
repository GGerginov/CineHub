package com.example.cinehub.service;

import com.example.cinehub.data.dtos.MovieDto;

import java.util.List;

public interface MovieService {

    List<MovieDto> findAllActualMovies();
}
