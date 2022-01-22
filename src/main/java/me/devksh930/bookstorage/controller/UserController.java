package me.devksh930.bookstorage.controller;

import lombok.RequiredArgsConstructor;
import me.devksh930.bookstorage.user.dto.UserSignUpDto;
import me.devksh930.bookstorage.user.dto.UserRequestDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @PostMapping
    public ResponseEntity<UserRequestDto> join(@RequestBody UserSignUpDto userDto) {

        return new ResponseEntity<>(userService.joinUser(userDto), HttpStatus.CREATED);
    }
}
