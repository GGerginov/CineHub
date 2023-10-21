package com.example.cinehub.data.validator;

import com.example.cinehub.data.anotation.CinemaSlugPresent;
import com.example.cinehub.repository.CinemaRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CinemaSlugPresentValidator implements ConstraintValidator<CinemaSlugPresent, String> {

    private final CinemaRepository cinemaRepository;

    @Autowired
    public CinemaSlugPresentValidator(CinemaRepository cinemaRepository) {
        this.cinemaRepository = cinemaRepository;
    }

    @Override
    public void initialize(CinemaSlugPresent constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String slug, ConstraintValidatorContext constraintValidatorContext) {

        return this.cinemaRepository.cinemaSlugExits(slug);
    }
}
