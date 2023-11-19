package com.example.cinehub.service.impl;

import com.example.cinehub.data.dtos.CinemaDto;
import com.example.cinehub.data.entity.Cinema;
import com.example.cinehub.exception.ApiException;
import com.example.cinehub.exception.jsonMessages.ErrorMessages;
import com.example.cinehub.repository.CinemaRepository;
import com.example.cinehub.service.CinemaService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Service implementation for cinema-related operations in the CineHub application.
 * <p>
 * This service provides methods for retrieving cinema information, including finding all cinemas
 * and finding cinemas by town name. It uses caching to improve performance.
 */
@Service
public class CinemaServiceImpl implements CinemaService {

    private final CinemaRepository cinemaRepository;

    private final ModelMapper modelMapper;

    private final Type listOfCinemaDtoType = new TypeToken<List<CinemaDto>>() {
    }.getType();

    /**
     * Constructs a CinemaServiceImpl with the specified cinema repository and model mapper.
     *
     * @param cinemaRepository The repository for handling cinema-related data operations.
     * @param modelMapper      The model mapper for converting between DTOs and entities.
     */
    @Autowired
    public CinemaServiceImpl(CinemaRepository cinemaRepository, ModelMapper modelMapper) {
        this.cinemaRepository = cinemaRepository;
        this.modelMapper = modelMapper;
    }

    /**
     * Retrieves a list of all cinemas.
     * <p>
     * This method returns a list of CinemaDto objects representing all cinemas.
     * The result is cached for improved performance.
     *
     * @return A list of CinemaDto objects.
     */
    @Override
    @Cacheable(value = "cinema", unless = "#result.isEmpty()")
    public List<CinemaDto> findAllCinemas() {

        List<Cinema> cinemas = this.cinemaRepository.findAll();

        return this.modelMapper.map(cinemas, this.listOfCinemaDtoType);
    }

    /**
     * Finds cinemas by their town name.
     * <p>
     * This method returns a list of CinemaDto objects representing cinemas found in the specified town.
     * The result is cached for improved performance.
     *
     * @param townName The name of the town for which to find cinemas.
     * @return A list of CinemaDto objects.
     * @throws ApiException if no cinemas are found for the given town name.
     */
    @Override
    @Cacheable(value = "cinemasByTownName", key = "#townName", unless = "#result.isEmpty()")
    public List<CinemaDto> findCinemasByTownName(String townName) throws ApiException {

        List<Cinema> cinemaByCityName = cinemaRepository.findByCityNameIgnoreCase(townName);

        if (cinemaByCityName.isEmpty()) {
            throw new ApiException(ErrorMessages.CINEMA_NOT_FOUND);
        }

        return this.modelMapper.map(cinemaByCityName, this.listOfCinemaDtoType);
    }


}
