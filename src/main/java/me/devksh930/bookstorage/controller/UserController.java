package me.devksh930.bookstorage.controller;

import lombok.RequiredArgsConstructor;
import me.devksh930.bookstorage.domain.User;
import me.devksh930.bookstorage.authentication.CurrentUser;
import me.devksh930.bookstorage.user.UserService;
import me.devksh930.bookstorage.user.dto.UserRequestDto;
import me.devksh930.bookstorage.user.dto.UserSignUpDto;
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
    public ResponseEntity<UserSignUpDto> signUp(@RequestBody UserSignUpDto userSignUpDto) {
        return new ResponseEntity(userService.joinUser(userSignUpDto), HttpStatus.CREATED);
    }

    @GetMapping("/me")
    public ResponseEntity<UserRequestDto> test(@CurrentUser User user) {
        return new ResponseEntity<>(modelMapper.map(user, UserRequestDto.class), HttpStatus.OK);
    }
}
