package com.example.hot_deal.common.util.validation.annotation;

import com.example.hot_deal.common.util.validation.validator.PasswordValidator;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.NotBlank;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = {PasswordValidator.class})
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@NotBlank(message = "비밀번호가 입력되지 않았습니다.")
@Schema(description = "회원 비밀번호", example = "Test1234test!")
public @interface ValidPassword {
    String message() default "유효하지 않은 비밀번호 형식입니다.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
