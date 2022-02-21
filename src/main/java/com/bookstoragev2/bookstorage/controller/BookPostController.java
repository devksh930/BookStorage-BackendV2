package com.bookstoragev2.bookstorage.controller;

import com.bookstoragev2.bookstorage.authentication.CurrentUser;
import com.bookstoragev2.bookstorage.bookpost.BookPostAddDto;
import com.bookstoragev2.bookstorage.bookpost.BookPostResponseDto;
import com.bookstoragev2.bookstorage.bookpost.BookPostService;
import com.bookstoragev2.bookstorage.common.util.ApiResult;
import com.bookstoragev2.bookstorage.common.util.ApiUtils;
import com.bookstoragev2.bookstorage.domain.BookPostType;
import com.bookstoragev2.bookstorage.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookPostController {
    private final BookPostService bookPostService;

    @PostMapping("/{bookStorageId}/posts")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResult<BookPostResponseDto> addBookPost(@CurrentUser User user, @PathVariable Long bookStorageId, @RequestBody BookPostAddDto bookPostAddDto) {
        return ApiUtils.success(bookPostService.addBookPost(user, bookStorageId, bookPostAddDto));
    }

    @GetMapping("/posts")
    public ApiResult<List<BookPostResponseDto>> getBookPostWithType(@RequestParam(value = "type") BookPostType bookPostType) {
        return ApiUtils.success(bookPostService.getBookPostWithType(bookPostType));
    }

    @GetMapping("/posts/{bookPostId}")
    public ApiResult<BookPostResponseDto> getBookPostDetails(@PathVariable Long bookPostId) {
        return ApiUtils.success(bookPostService.getBookPostDetails(bookPostId));
    }

    @PatchMapping("/posts/{bookPostId}")
    public ApiResult<BookPostResponseDto> modifyPost(@CurrentUser User user, @PathVariable Long bookPostId, @RequestBody BookPostAddDto bookPostAddDto) {
        return ApiUtils.success(bookPostService.modifyPost(user, bookPostId, bookPostAddDto));
    }
}
