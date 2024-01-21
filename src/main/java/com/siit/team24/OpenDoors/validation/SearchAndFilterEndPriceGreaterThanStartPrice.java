package com.siit.team24.OpenDoors.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = SearchAndFilterEndPriceGreaterThanStartPriceValidator.class)
public @interface SearchAndFilterEndPriceGreaterThanStartPrice {
    String message() default "End price must be greater than start price";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}