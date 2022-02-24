package com.bookstoragev2.bookstorage.controller;

import com.bookstoragev2.bookstorage.authentication.CurrentUser;
import com.bookstoragev2.bookstorage.bookpost.BookPostService;
import com.bookstoragev2.bookstorage.bookpost.dto.BookPostAddDto;
import com.bookstoragev2.bookstorage.bookpost.dto.BookPostResponseDto;
import com.bookstoragev2.bookstorage.common.util.ApiResult;
import com.bookstoragev2.bookstorage.common.util.ApiUtils;
import com.bookstoragev2.bookstorage.domain.BookPost;
import com.bookstoragev2.bookstorage.domain.BookPostType;
import com.bookstoragev2.bookstorage.domain.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class BookPostController {

    private final BookPostService bookPostService;
    private final ModelMapper modelMapper;

    //    로그인된 사용자가 등록된 bookstorage를 통해서 bookPost를 작성
    @PostMapping("/bookstorage/{bookStorageId}/bookposts")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResult<BookPostResponseDto> addBookPost(@CurrentUser User user, @PathVariable Long bookStorageId, @RequestBody BookPostAddDto bookPostAddDto) {
        return ApiUtils.success(bookPostService.addBookPost(user, bookStorageId, bookPostAddDto));
    }

    //로그인된 사용자가 bookPost의 게시글을 수정한다
    @PatchMapping("/bookposts/{bookPostId}")
    public ApiResult<BookPostResponseDto> modifyPost(@CurrentUser User user, @PathVariable Long bookPostId, @RequestBody BookPostAddDto bookPostAddDto) {
        return ApiUtils.success(bookPostService.modifyPost(user, bookPostId, bookPostAddDto));
    }

    //bookPost의 ID로 게시글을 상세 조회한다.
    @GetMapping("/bookposts/{bookPostId}")
    public ApiResult<BookPostResponseDto> getBookPostDetails(@PathVariable Long bookPostId) {
        return ApiUtils.success(bookPostService.getBookPostDetails(bookPostId));
    }

    //    BookPostType으로 게시글을 가저온다
    @GetMapping("/bookposts")
    public ApiResult<List<BookPostResponseDto>> getBookPostWithType(@RequestParam(value = "type") BookPostType bookPostType,
                                                                    @RequestParam(value = "size", defaultValue = "30") int size,
                                                                    @RequestParam(value = "page", defaultValue = "0") int page) {

        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("createdDate").descending());

        Page<BookPost> bookPostWithType = bookPostService.getBookPostWithType(bookPostType, pageRequest);
        List<BookPostResponseDto> bookPostResponse = bookPostWithType.getContent().stream().map(BookPostResponseDto::new).collect(Collectors.toList());
        return ApiUtils.successPaging(bookPostResponse, bookPostWithType.getSize(), bookPostWithType.getPageable().getPageNumber(), bookPostWithType.getTotalElements());
    }

    //    로그인된 유저가 작성한 BookPost를 가져온다
    @GetMapping("/users/bookposts")
    public ApiResult<List<BookPostResponseDto>> getBookPostWithCurrentUser(@CurrentUser User user,
                                                                           @RequestParam(value = "size", defaultValue = "30") int size,
                                                                           @RequestParam(value = "page", defaultValue = "0") int page) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("createdDate").descending());

        Page<BookPost> bookPostWithCurrentUser = bookPostService.getBookPostWithCurrentUser(user, pageRequest);
        List<BookPostResponseDto> collect = bookPostWithCurrentUser.getContent().stream().map(BookPostResponseDto::new).collect(Collectors.toList());

        return ApiUtils.successPaging(collect, bookPostWithCurrentUser.getSize(), bookPostWithCurrentUser.getPageable().getPageNumber(), bookPostWithCurrentUser.getTotalElements());
    }

}
