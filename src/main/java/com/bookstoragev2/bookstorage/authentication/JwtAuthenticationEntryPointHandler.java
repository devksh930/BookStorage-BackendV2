package com.bookstoragev2.bookstorage.authentication;

import com.bookstoragev2.bookstorage.error.ErrorCode;
import com.bookstoragev2.bookstorage.error.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

@Component
@Slf4j
public class JwtAuthenticationEntryPointHandler implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        if (authException.getClass().equals(BadCredentialsException.class)) {
            log.error("BadCredentialException {}", authException);
            throw new BadCredentialsException("");
        }
        log.error("AuthenticationExcpeiton {}", authException);
        final ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.HANDLE_AUTHENTICATION_ENTRYPOINT);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(errorResponse.getStatus());
        try (OutputStream os = response.getOutputStream()) {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(os, errorResponse);
            os.flush();
        }
    }
}
