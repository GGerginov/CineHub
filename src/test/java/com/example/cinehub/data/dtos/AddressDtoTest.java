package com.example.cinehub.data.dtos;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class AddressDtoTest {

    private UUID id;
    private String cityName;
    private String street;

    private Validator validator;

    @BeforeEach
    void setUp() {
        this.id = UUID.randomUUID();
        this.cityName = "Test City";
        this.street = "Test Street";

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testNoArgsConstructor() {
        AddressDto addressDto = new AddressDto();

        assertNull(addressDto.getId());
        assertNull(addressDto.getCityName());
        assertNull(addressDto.getStreet());
    }

    @Test
    void testAllArgsConstructor() {
        AddressDto addressDto = new AddressDto(id, cityName, street);

        assertEquals(id, addressDto.getId());
        assertEquals(cityName, addressDto.getCityName());
        assertEquals(street, addressDto.getStreet());
    }

    @Test
    void testNotNullConstraints() {
        AddressDto addressDto = new AddressDto(null, null, null);

        Set<ConstraintViolation<AddressDto>> violations = validator.validate(addressDto);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("can not be null")));
    }

    @Test
    void testNotBlankConstraints() {
        AddressDto addressDto = new AddressDto(id, "", "");

        Set<ConstraintViolation<AddressDto>> violations = validator.validate(addressDto);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("can not be blank")));
    }

}
