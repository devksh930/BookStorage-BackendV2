package com.bookstoragev2.bookstorage.controller;

import com.bookstoragev2.bookstorage.authentication.CurrentUser;
import com.bookstoragev2.bookstorage.bookpost.BookPostResponseDto;
import com.bookstoragev2.bookstorage.bookpost.BookPostService;
import com.bookstoragev2.bookstorage.bookstorage.BookStorageService;
import com.bookstoragev2.bookstorage.bookstorage.dto.BookStorageAddDto;
import com.bookstoragev2.bookstorage.bookstorage.dto.BookStorageResponseDto;
import com.bookstoragev2.bookstorage.common.util.ApiResult;
import com.bookstoragev2.bookstorage.common.util.ApiUtils;
import com.bookstoragev2.bookstorage.domain.User;
import com.bookstoragev2.bookstorage.user.UserService;
import com.bookstoragev2.bookstorage.user.dto.UserRequestDto;
import com.bookstoragev2.bookstorage.user.dto.UserSignUpDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper;
    private final BookStorageService bookStorageService;
    private final BookPostService bookPostService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResult<UserRequestDto> signUp(@RequestBody UserSignUpDto userSignUpDto) {
        return ApiUtils.success(userService.joinUser(userSignUpDto));
    }

    @GetMapping("/me")
    public ApiResult<UserRequestDto> getLoginUserinfo(@CurrentUser User user) {
        return ApiUtils.success(modelMapper.map(user, UserRequestDto.class));
    }

    @PostMapping("/books")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResult<BookStorageResponseDto> addBookstorage(@CurrentUser User user, @RequestBody BookStorageAddDto bookStorageAddDto) {
        return ApiUtils.success(bookStorageService.addBookStorage(user, bookStorageAddDto));
    }

    @GetMapping("/books")
    public ApiResult<List<BookStorageResponseDto>> getBookStorage(@CurrentUser User user) {
        return ApiUtils.success(bookStorageService.getBookStorage(user));
    }

    @GetMapping("/bookposts")
    public ApiResult<List<BookPostResponseDto>> getBookPostWithCurrentUser(@CurrentUser User user) {
        return ApiUtils.success(bookPostService.getBookPostWithCurrentUser(user));
    }
}
