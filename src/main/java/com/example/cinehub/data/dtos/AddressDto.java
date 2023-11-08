package com.example.cinehub.data.dtos;

import com.example.cinehub.data.entity.Address;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.UUID;

/**
 * DTO for {@link Address}
 */

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AddressDto implements Serializable {

    private UUID id;

    @NotNull(message = "Name can not be null")
    @NotBlank(message = "Name can not be blank")
    private String cityName;

    @NotNull(message = "Street can be null")
    @NotBlank(message = "Street can not be blank")
    private String street;
}