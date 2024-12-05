package com.example.hot_deal.common.config.web;

import com.example.hot_deal.common.domain.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@Slf4j
@RestControllerAdvice
public class ResponseWrapper implements ResponseBodyAdvice<Object> {


    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        log.info("2# Execute ResponseWrapper - supports");
        log.info("2# Execute ResponseWrapper - returnType :: {}", returnType);
        log.info("2# Execute ResponseWrapper - converterType :: {}", converterType);

        // 현재 요청 URI 확인
        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attrs != null) {
            String requestUri = attrs.getRequest().getRequestURI();
            if (requestUri.startsWith("/v3/api-docs")) {
                log.info("Skipping ResponseWrapper for Swagger-related requests: {}", requestUri);
                return false; // Swagger 경로는 처리하지 않음
            }
        }

        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body,
                                  MethodParameter returnType,
                                  MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request,
                                  ServerHttpResponse response
    ) {
        log.info("3# Execute ResponseWrapper - beforeBodyWrite");
        if (body instanceof ErrorResponse) {
            return new ApiResponse<>("ERROR", body);
        }
        return new ApiResponse<>("SUCCESS", body);
    }
}
