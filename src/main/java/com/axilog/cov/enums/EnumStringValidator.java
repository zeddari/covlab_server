package com.axilog.cov.enums;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;

import com.axilog.cov.validator.ValidateStringEnum;

public class EnumStringValidator implements ConstraintValidator<ValidateStringEnum, String> {

	private List<String> valueList;

	@Override
	public void initialize(ValidateStringEnum constraintAnnotation) {
		valueList = new ArrayList<>();
		for (String val : constraintAnnotation.acceptedValues()) {
			String[] values = val.split(",");
			for (String value : values) {
				valueList.add(value);
			}
		}
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (StringUtils.isNotEmpty(value)) {
			return valueList.contains(value);
		}
		return true;
	}

}