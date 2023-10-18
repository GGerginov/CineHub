package com.example.cinehub.service.impl;

import com.example.cinehub.data.dtos.CinemaDto;
import com.example.cinehub.data.entity.Cinema;
import com.example.cinehub.repository.CinemaRepository;
import com.example.cinehub.service.CinemaService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;

@Service
public class CinemaServiceImpl implements CinemaService {

    private final CinemaRepository cinemaRepository;

    private final ModelMapper modelMapper;

    @Autowired
    public CinemaServiceImpl(CinemaRepository cinemaRepository, ModelMapper modelMapper) {
        this.cinemaRepository = cinemaRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<CinemaDto> findAllCinemas() {

        List<Cinema> cinemas = this.cinemaRepository.findAll();
        Type listOfCinemaDtoType = new TypeToken<List<CinemaDto>>() {
        }.getType();

        List<CinemaDto> cinemaDtos = this.modelMapper.map(cinemas,listOfCinemaDtoType);

        return cinemaDtos;
    }
}
