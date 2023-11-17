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

class MovieDtoTest {

    private UUID id;
    private String title;
    private String description;
    private Integer duration;

    private Validator validator;

    @BeforeEach
    void setUp() {
        this.id = UUID.randomUUID();
        this.title = "Test Movie";
        this.description = "This is a test movie description.";
        this.duration = 120;

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testNoArgsConstructor() {
        MovieDto movieDto = new MovieDto();

        assertNull(movieDto.getId());
        assertNull(movieDto.getTitle());
        assertNull(movieDto.getDescription());
        assertNull(movieDto.getDuration());
    }

    @Test
    void testAllArgsConstructor() {
        MovieDto movieDto = new MovieDto(id, title, description, duration);

        assertEquals(id, movieDto.getId());
        assertEquals(title, movieDto.getTitle());
        assertEquals(description, movieDto.getDescription());
        assertEquals(duration, movieDto.getDuration());
    }

    @Test
    void testNotNullAndNotBlankConstraints() {
        MovieDto movieDto = new MovieDto(null, null, description, null);

        Set<ConstraintViolation<MovieDto>> violations = validator.validate(movieDto);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("can not be null")));
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("can not be blank")));
    }

    @Test
    void testPositiveConstraint() {
        MovieDto movieDto = new MovieDto(id, title, description, -10);

        Set<ConstraintViolation<MovieDto>> violations = validator.validate(movieDto);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("can not be negative")));
    }
}
