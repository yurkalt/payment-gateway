package com.example.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class ExpiryDateValidator implements
        ConstraintValidator<ExpiryDateConstraint, String> {
    @Override
    public void initialize(ExpiryDateConstraint constraintAnnotation) {

    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        try {
            Date expiryDate =new SimpleDateFormat("MMyy").parse(s);
            LocalDate todaydate = LocalDate.now();
            LocalDate localDate = todaydate.withDayOfMonth(1);
            Date now = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            if(expiryDate.equals(now) || expiryDate.after(now)){
                return true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }
}
