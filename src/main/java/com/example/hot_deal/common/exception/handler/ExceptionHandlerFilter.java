package com.example.hot_deal.common.exception.handler;

import com.example.hot_deal.common.exception.ErrorResponse;
import com.example.hot_deal.common.exception.code.ErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
@Component
@RequiredArgsConstructor
public class ExceptionHandlerFilter {
    private static final String LOG_FORMAT = "\nException Class = {}\nResponse Code = {}\nMessage = {}";
    private final ObjectMapper objectMapper;


    private void handleErrorResponse(Exception exception, HttpServletResponse response, ErrorCode errorCode){
        logException(exception, errorCode);
        response.setStatus(errorCode.getHttpStatus().value());
        response.setContentType("application/json;charset=UTF-8");

        ErrorResponse errorResponse = ErrorResponse.from(errorCode.getHttpStatus(), errorCode.getMessage());
        try {
            PrintWriter writer = response.getWriter();
            writer.write(objectMapper.writeValueAsString(errorResponse));
            writer.flush();
        } catch (IOException e) {
            log.error("Error writing response: {}", e.getMessage(), e);
        }
    }

    private void logException(final Exception e, final ErrorCode errorCode) {
        log.error(LOG_FORMAT, e.getClass(), errorCode.getHttpStatus().value(), errorCode.getMessage());
    }
}
