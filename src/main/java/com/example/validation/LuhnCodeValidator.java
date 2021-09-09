package com.example.validation;

import org.apache.commons.validator.routines.checkdigit.LuhnCheckDigit;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class LuhnCodeValidator implements
        ConstraintValidator<LuhnCodeConstraint, String> {
    @Override
    public void initialize(LuhnCodeConstraint constraintAnnotation) {

    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return LuhnCheckDigit.LUHN_CHECK_DIGIT.isValid(s);
    }
}
