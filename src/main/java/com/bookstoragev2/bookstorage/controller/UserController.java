package com.bookstoragev2.bookstorage.controller;

import com.bookstoragev2.bookstorage.authentication.CurrentUser;
import com.bookstoragev2.bookstorage.domain.User;
import com.bookstoragev2.bookstorage.user.UserService;
import com.bookstoragev2.bookstorage.user.dto.UserRequestDto;
import com.bookstoragev2.bookstorage.user.dto.UserSignUpDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper;


    @PostMapping
    public ResponseEntity<UserRequestDto> signUp(@RequestBody UserSignUpDto userSignUpDto) {
        return new ResponseEntity<>(userService.joinUser(userSignUpDto), HttpStatus.CREATED);
    }

    @GetMapping("/me")
    public ResponseEntity<UserRequestDto> getLoginUserinfo(@CurrentUser User user) {
        return new ResponseEntity<>(modelMapper.map(user, UserRequestDto.class), HttpStatus.OK);
    }
}
