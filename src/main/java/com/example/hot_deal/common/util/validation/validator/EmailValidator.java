package com.example.hot_deal.common.util.validation.validator;

import com.example.hot_deal.common.util.validation.annotaion.ValidEmail;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

import static com.example.hot_deal.common.util.validation.validator.ValidatePatterns.EMAIL_REGEX;

public class EmailValidator implements ConstraintValidator<ValidEmail, String> {

    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }
}
