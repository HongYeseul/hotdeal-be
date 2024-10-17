package com.example.hot_deal.common.util.validation.validator;

import com.example.hot_deal.common.util.validation.annotation.ValidPassword;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

import static com.example.hot_deal.common.util.validation.validator.ValidatePatterns.PASSWORD_REGEX;

public class PasswordValidator implements ConstraintValidator<ValidPassword, String> {

    private static final Pattern PASSWORD_PATTERN = Pattern.compile(PASSWORD_REGEX);

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        return password != null && PASSWORD_PATTERN.matcher(password).matches();
    }
}
