package me.devksh930.bookstorage.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/exception")
public class ErrorController {
    @GetMapping("/entrypoint")
    public ResponseEntity<String> authenticationEntryPoint() {

        return new ResponseEntity("권한없음", HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/accessdenied")
    public ResponseEntity<String> accessDenied() {
        return new ResponseEntity("권한없음", HttpStatus.FORBIDDEN);
    }
}
