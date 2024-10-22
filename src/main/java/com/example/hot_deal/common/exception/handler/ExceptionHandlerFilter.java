package com.example.hot_deal.common.exception.handler;

import com.example.hot_deal.auth.constants.error.AuthErrorCode;
import com.example.hot_deal.common.exception.ErrorResponse;
import com.example.hot_deal.common.exception.code.ErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
@Component
@RequiredArgsConstructor
public class ExceptionHandlerFilter extends OncePerRequestFilter {

    private static final String LOG_FORMAT = "\nException Class = {}\nResponse Code = {}\nMessage = {}";
    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException e) {
            handleErrorResponse(e, response, AuthErrorCode.TOKEN_EXPIRED);
        } catch (JwtException | IllegalArgumentException e) {
            handleErrorResponse(e, response, AuthErrorCode.TOKEN_INVALID);
        }
    }

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
