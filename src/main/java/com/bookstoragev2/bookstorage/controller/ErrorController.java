package com.bookstoragev2.bookstorage.controller;

import com.bookstoragev2.bookstorage.error.exception.CustomAuthenticationEntryPointException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/exception")
public class ErrorController {
    @GetMapping("/entrypoint")
    public ResponseEntity authenticationEntryPoint() {
        throw new CustomAuthenticationEntryPointException();
    }

    @GetMapping("/accessdenied")
    public ResponseEntity<String> accessDenied() {
        throw new AccessDeniedException("");
    }
}
