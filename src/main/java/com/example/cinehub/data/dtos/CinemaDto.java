package com.example.cinehub.data.dtos;

import com.example.cinehub.data.entity.Cinema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

/**
 * Data Transfer Object (DTO) for the {@link Cinema} entity.
 * <p>
 * This class serves as a simplified, serializable representation of a Cinema, facilitating the transfer
 * of cinema-related data between different layers of the application, especially in API communications.
 * It abstracts the internal details of the Cinema entity and provides a client-friendly data model.
 * <p>
 * The class includes validation annotations to ensure the integrity and correctness of the data
 * received from or sent to clients.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CinemaDto implements Serializable {

    /**
     * Unique identifier for the cinema.
     */
    private UUID id;

    /**
     * Name of the cinema. This field is mandatory and cannot be null or blank.
     */
    @NotNull(message = "Name can not be null")
    @NotBlank(message = "Name can not be blank")
    private String name;

    /**
     * Unique slug for the cinema. This field is mandatory and cannot be null or blank.
     * The slug is used as an identifier in client-server communication.
     */
    @NotNull(message = "Slug can not be null")
    @NotBlank(message = "Slug can not be blank")
    private String slug;

    /**
     * Address details of the cinema. This field is mandatory and cannot be null.
     * The address is represented as an {@link AddressDto} object.
     */
    @NotNull(message = "Address can not be null")
    private AddressDto address;

}