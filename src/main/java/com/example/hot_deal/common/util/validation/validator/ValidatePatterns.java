package com.example.hot_deal.common.util.validation.validator;

public class ValidatePatterns {
    public static final String NAME_REGEX = "^[ㄱ-ㅎㅏ-ㅣ가-힣a-zA-Z0-9!@#$%^&*()_+\\-=?<>]{2,6}$";
    public static final String PASSWORD_REGEX = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d@$!%*#?&]{8,}$";
    public static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
}
