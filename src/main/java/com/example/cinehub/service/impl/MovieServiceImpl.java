package com.example.cinehub.service.impl;

import com.example.cinehub.data.dtos.MovieDto;
import com.example.cinehub.data.entity.Movie;
import com.example.cinehub.repository.MovieRepository;
import com.example.cinehub.service.MovieService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;

    private final ModelMapper modelMapper;

    @Autowired
    public MovieServiceImpl(MovieRepository movieRepository, ModelMapper modelMapper) {
        this.movieRepository = movieRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<MovieDto> findAllActualMovies() {

        List<Movie> movies = this.movieRepository.findAllActualMovies();

        Type listOfMovieDtoType = new TypeToken<List<MovieDto>>() {
        }.getType();


        return this.modelMapper.map(movies,listOfMovieDtoType);
    }
}
