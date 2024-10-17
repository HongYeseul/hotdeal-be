package com.example.hot_deal.common.util.validation.validator;

import com.example.hot_deal.common.util.validation.annotation.ValidName;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

import static com.example.hot_deal.common.util.validation.validator.ValidatePatterns.NAME_REGEX;

public class NameValidator implements ConstraintValidator<ValidName, String> {

    private static final Pattern NAME_PATTERN = Pattern.compile(NAME_REGEX);

    @Override
    public boolean isValid(String name, ConstraintValidatorContext context) {
        if (name == null || name.isBlank()) {
            return false;  // 이름이 비어 있거나 null 이면 false 반환
        }

        return NAME_PATTERN.matcher(name).matches();
    }
}
