package com.bookstoragev2.bookstorage.controller;

import com.bookstoragev2.bookstorage.book.BookDto;
import com.bookstoragev2.bookstorage.book.NaverApiClient;
import com.bookstoragev2.bookstorage.common.util.ApiResult;
import com.bookstoragev2.bookstorage.common.util.ApiUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {
    private final NaverApiClient naverApiClient;

    @GetMapping
    public ApiResult<BookDto> searchBook(@RequestParam String query, @RequestParam int start) {
        return ApiUtils.success(naverApiClient.getBookToQuery(query, start).getBody());
    }
}

