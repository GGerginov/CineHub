package com.example.cinehub.data.dtos;

import java.io.Serializable;

/**
 * DTO for {@link com.example.cinehub.data.entity.Cinema}
 */
public record CinemaDto(String id, String name, String address, CityDto city) implements Serializable {
}