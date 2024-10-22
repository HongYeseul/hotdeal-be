package com.example.hot_deal.auth.configuration;


import com.example.hot_deal.auth.constants.error.AuthErrorCode;
import com.example.hot_deal.common.exception.HotDealException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Slf4j
@Component
@RequiredArgsConstructor
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(LoginUser.class) &&
                Long.class.isAssignableFrom(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                              NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("인증 시도 중인 사용자: {}", authentication.getName());
        if (!authentication.isAuthenticated() || authentication instanceof AnonymousAuthenticationToken) {
            throw new HotDealException(AuthErrorCode.LOGIN_REQUIRED);
        }
        Object principal = authentication.getPrincipal();
        if (principal instanceof CustomUserDetails userDetails) {
            return userDetails.getMember().getId();
        }
        log.error("인증된 사용자의 principal이 CustomUserDetails 타입과 다릅니다. 실제 타입: {}", principal.getClass().getName());
        throw new HotDealException(AuthErrorCode.INVALID_AUTHENTICATION_STATE);
    }
}

