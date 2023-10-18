package com.example.cinehub.data.dtos;

import com.example.cinehub.data.entity.City;

import java.io.Serializable;

/**
 * DTO for {@link City}
 */
public record CityDto(String id, String name) implements Serializable {
}