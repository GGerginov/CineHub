package com.example.cinehub.data.validator;

import com.example.cinehub.data.anotation.CinemaSlugExist;
import com.example.cinehub.repository.CinemaRepository;
import org.springframework.beans.factory.annotation.Autowired;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CinemaSlugExistValidator implements ConstraintValidator<CinemaSlugExist, String> {

    private final CinemaRepository cinemaRepository;

    @Autowired
    public CinemaSlugExistValidator(CinemaRepository cinemaRepository) {
        this.cinemaRepository = cinemaRepository;
    }

    @Override
    public void initialize(CinemaSlugExist constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String slug, ConstraintValidatorContext constraintValidatorContext) {

        return this.cinemaRepository.cinemaSlugExits(slug);
    }
}
