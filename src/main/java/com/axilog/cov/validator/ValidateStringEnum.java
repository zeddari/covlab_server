package com.axilog.cov.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.axilog.cov.enums.EnumStringValidator;

@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EnumStringValidator.class)
public @interface ValidateStringEnum {

	String[] acceptedValues();

	String message() default "{com.gemalto.ics.dc.idms.thailand.dto.command.validator.ValidateString.message}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}