package com.example.hot_deal.common.util.validation.annotaion;

import com.example.hot_deal.common.util.validation.validator.NameValidator;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = {NameValidator.class})
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@NotBlank(message = "이름은 비어 있을 수 없습니다.")
@Size(min = 2, max = 6, message = "이름은 2자 이상 6자 이하여야 합니다.")
@Schema(description = "회원 이름", example = "홍길동")
public @interface ValidName {
    String message() default "유효하지 않은 이름 형식입니다.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
