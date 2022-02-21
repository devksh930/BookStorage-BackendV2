package com.bookstoragev2.bookstorage.controller;

import com.bookstoragev2.bookstorage.book.BookDto;
import com.bookstoragev2.bookstorage.book.BookItemDto;
import com.bookstoragev2.bookstorage.book.BookService;
import com.bookstoragev2.bookstorage.common.util.ApiResult;
import com.bookstoragev2.bookstorage.common.util.ApiUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;
    @GetMapping
    public ApiResult<BookDto> searchBook(@RequestParam String query, @RequestParam int start) {
        return ApiUtils.success(bookService.searchBook(query, start));
    }

    @GetMapping("/{isbn}")
    public ApiResult<BookItemDto> searchBookDetail(@PathVariable String isbn) {
        return ApiUtils.success(bookService.searchBookDetail(isbn));
    }
}

