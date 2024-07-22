package com.sparta.icticket.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.icticket.common.enums.ErrorType;
import com.sparta.icticket.common.exception.ExceptionDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        ExceptionDto exceptionDto = new ExceptionDto(ErrorType.INVALID_ACCESS);

        response.setStatus(exceptionDto.getErrorType().getHttpStatus().value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(new ObjectMapper().writeValueAsString(exceptionDto));
    }
}

