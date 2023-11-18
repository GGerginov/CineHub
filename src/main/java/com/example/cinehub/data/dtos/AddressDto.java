package com.example.cinehub.data.dtos;

import com.example.cinehub.data.entity.Address;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

/**
 * Data Transfer Object (DTO) for the {@link Address} entity.
 * <p>
 * This class is used to transfer address data between different layers of the application,
 * particularly for API communication. It abstracts the internal representation of the Address entity
 * and exposes only the necessary data for the client-side or other services.
 * <p>
 * The class includes validation annotations to ensure that the data received from or sent to clients
 * meets the application's data integrity requirements.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AddressDto implements Serializable {

    /**
     * Unique identifier for the address.
     */
    private UUID id;

    /**
     * Name of the city. This field cannot be null or blank.
     */
    @NotNull(message = "Name can not be null")
    @NotBlank(message = "Name can not be blank")
    private String cityName;

    /**
     * Street name. This field cannot be null or blank.
     */
    @NotNull(message = "Street can be null")
    @NotBlank(message = "Street can not be blank")
    private String street;
}