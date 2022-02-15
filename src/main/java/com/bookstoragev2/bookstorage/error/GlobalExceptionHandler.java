package com.bookstoragev2.bookstorage.error;

import com.bookstoragev2.bookstorage.error.exception.CustomAuthenticationEntryPointException;
import com.bookstoragev2.bookstorage.error.exception.NotFoundBookDetail;
import com.bookstoragev2.bookstorage.error.exception.UserJoinExistException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    //    Common
    @ExceptionHandler(AccessDeniedException.class)
    protected ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException e) {
        log.error("Handle AccessDeniedException ", e);
        final ErrorResponse response = ErrorResponse.of(ErrorCode.HANDLE_ACCESS_DENIED);
        return new ResponseEntity<>(response, HttpStatus.valueOf(ErrorCode.HANDLE_ACCESS_DENIED.getStatus()));
    }

    @ExceptionHandler(CustomAuthenticationEntryPointException.class)
    protected ResponseEntity<ErrorResponse> handleAuthenticationException(CustomAuthenticationEntryPointException e) {
        log.error("Handle AuthenticationException ", e);
        final ErrorResponse response = ErrorResponse.of(ErrorCode.HANDLE_AUTHENTICATION_ENTRYPOINT);
        return new ResponseEntity<>(response, HttpStatus.valueOf(ErrorCode.HANDLE_AUTHENTICATION_ENTRYPOINT.getStatus()));
    }

    //    User
    @ExceptionHandler(BadCredentialsException.class)
    protected ResponseEntity<ErrorResponse> handleAuthenticationException(BadCredentialsException e) {
        log.error("Handle LoginInputInvalidException ",e);
        final ErrorResponse response = ErrorResponse.of(ErrorCode.LOGIN_INPUT_INVALID);
        return new ResponseEntity<>(response, HttpStatus.valueOf(ErrorCode.LOGIN_INPUT_INVALID.getStatus()));
    }

    @ExceptionHandler(UserJoinExistException.class)
    protected ResponseEntity<ErrorResponse> handUserJoinExistException(UserJoinExistException e) {
        log.error("Handle UserJoinExistException ", e);
        final ErrorResponse response = ErrorResponse.of(ErrorCode.USER_EXISTS_JOIN);
        return new ResponseEntity<>(response, HttpStatus.valueOf(ErrorCode.USER_EXISTS_JOIN.getStatus()));
    }

    // BOOK
    @ExceptionHandler(NotFoundBookDetail.class)
    protected ResponseEntity<ErrorResponse> handleBookDetailNotFound(NotFoundBookDetail e) {
        log.error("Handle NotFoundBookDetail ", e);
        final ErrorResponse response = ErrorResponse.of(ErrorCode.BOOK_DETAIL_NOTFOUND);
        return new ResponseEntity<>(response, HttpStatus.valueOf(ErrorCode.BOOK_DETAIL_NOTFOUND.getStatus()));
    }
}
