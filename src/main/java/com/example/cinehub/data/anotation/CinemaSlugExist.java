package com.example.cinehub.data.anotation;


import com.example.cinehub.data.validator.CinemaSlugExistValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {CinemaSlugExistValidator.class})
public @interface CinemaSlugExist {

    String message() default "Cinema slug does not exist!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
