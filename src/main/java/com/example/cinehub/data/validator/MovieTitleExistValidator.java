package com.example.cinehub.data.validator;

import com.example.cinehub.data.anotation.MovieTitleExist;
import com.example.cinehub.repository.MovieRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class MovieTitleExistValidator  implements ConstraintValidator<MovieTitleExist, String> {


    private final MovieRepository movieRepository;

    @Autowired
    public MovieTitleExistValidator(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public void initialize(MovieTitleExist constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String title, ConstraintValidatorContext constraintValidatorContext) {

        return this.movieRepository.existsByTitle(title);
    }
}
