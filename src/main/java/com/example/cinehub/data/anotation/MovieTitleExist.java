package com.example.cinehub.data.anotation;


import com.example.cinehub.data.validator.MovieTitleExistValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {MovieTitleExistValidator.class})
public @interface MovieTitleExist {

    String message() default "Movie title does not exist!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
