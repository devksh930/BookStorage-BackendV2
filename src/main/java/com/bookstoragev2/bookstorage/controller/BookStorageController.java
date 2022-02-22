package com.bookstoragev2.bookstorage.controller;

import com.bookstoragev2.bookstorage.authentication.CurrentUser;
import com.bookstoragev2.bookstorage.bookstorage.BookStorageService;
import com.bookstoragev2.bookstorage.bookstorage.dto.BookStorageAddDto;
import com.bookstoragev2.bookstorage.bookstorage.dto.BookStorageResponseDto;
import com.bookstoragev2.bookstorage.common.util.ApiResult;
import com.bookstoragev2.bookstorage.common.util.ApiUtils;
import com.bookstoragev2.bookstorage.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookstorage")
@RequiredArgsConstructor
public class BookStorageController {

    private final BookStorageService bookStorageService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResult<BookStorageResponseDto> addBookstorage(@CurrentUser User user, @RequestBody BookStorageAddDto bookStorageAddDto) {
        return ApiUtils.success(bookStorageService.addBookStorage(user, bookStorageAddDto));
    }

    @GetMapping
    public ApiResult<List<BookStorageResponseDto>> getBookStorage(@CurrentUser User user) {
        return ApiUtils.success(bookStorageService.getBookStorage(user));
    }

}
