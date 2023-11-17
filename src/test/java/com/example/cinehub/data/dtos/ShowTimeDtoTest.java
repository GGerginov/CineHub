package com.example.cinehub.data.dtos;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ShowTimeDtoTest {

    private UUID id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private MovieDto movie;

    private Validator validator;

    @BeforeEach
    void setUp() {
        this.id = UUID.randomUUID();
        this.startTime = LocalDateTime.now();
        this.endTime = LocalDateTime.now().plusHours(2);
        this.movie = new MovieDto();

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testNoArgsConstructor() {
        ShowTimeDto showTimeDto = new ShowTimeDto();

        assertNull(showTimeDto.getId());
        assertNull(showTimeDto.getStartTime());
        assertNull(showTimeDto.getEndTime());
        assertNull(showTimeDto.getMovie());
    }

    @Test
    void testAllArgsConstructor() {
        ShowTimeDto showTimeDto = new ShowTimeDto(id, startTime, endTime, movie);

        assertEquals(id, showTimeDto.getId());
        assertEquals(startTime, showTimeDto.getStartTime());
        assertEquals(endTime, showTimeDto.getEndTime());
        assertEquals(movie, showTimeDto.getMovie());
    }

    @Test
    void testNotNullConstraints() {
        ShowTimeDto showTimeDto = new ShowTimeDto(null, null, null, null);

        Set<ConstraintViolation<ShowTimeDto>> violations = validator.validate(showTimeDto);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("can not be null")));
    }

}
