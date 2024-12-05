package com.example.hot_deal.common.config.web;

import com.example.hot_deal.common.domain.ApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE) // 순서대로 컨버터가 등록 되는데, 최우선 순위로 등록
public class CommonHttpMessageConverter extends AbstractHttpMessageConverter<ApiResponse<Object>> {

    private final ObjectMapper objectMapper;

    public CommonHttpMessageConverter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    /**
     * supports가 true를 return 하면 해당 컨버터를 실행하게 됨
     */
    @Override
    protected boolean supports(Class<?> clazz) {
        log.info("1# CommonHttpMessageConverter - supports ");

        // ApiResponse를 상속/구현하는 클래스까지 true 반환
        return ApiResponse.class.isAssignableFrom(clazz);

        // 특정 클래스인지 확인
        // return clazz.equals(ApiResponse.class) || clazz.isPrimitive() || clazz.equals(String.class);
    }

    @Override
    protected ApiResponse<Object> readInternal(Class<? extends ApiResponse<Object>> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        return null;
    }

    /**
     * HTTP 응답을 생성하는 메서드
     * ApiResponse 객체를 JSON 형식으로 변환한 뒤 HTTP 응답
     * @param resultMessage the object to write to the output message
     * @param outputMessage the HTTP output message to write to
     */
    @Override
    protected void writeInternal(ApiResponse<Object> resultMessage, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        log.info("4# CommonHttpMessageConverter - writeInternal ");

        String responseMessage = this.objectMapper.writeValueAsString(resultMessage); // JSON 포맷 변환
        StreamUtils.copy(responseMessage.getBytes(StandardCharsets.UTF_8), outputMessage.getBody());
    }
}
