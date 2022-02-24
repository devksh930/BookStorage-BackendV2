package com.bookstoragev2.bookstorage.bookpost.dto;

import com.bookstoragev2.bookstorage.domain.BookPost;
import com.bookstoragev2.bookstorage.domain.BookPostType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BookPostResponseDto {
    private Long id;

    private String title;

    private String writer;

    private String content;

    private int count;

    private BookPostType bookPostType;

    private LocalDateTime createDate;

    private LocalDateTime modifiedDate;

    public BookPostResponseDto(BookPost bookPost) {
        this.id = bookPost.getId();
        this.title = bookPost.getTitle();
        this.writer = bookPost.getBookStorage().getUser().getNickname();
        this.content = bookPost.getContent();
        this.count = bookPost.getCount();
        this.bookPostType = bookPost.getBookPostType();
        this.createDate = bookPost.getCreatedDate();
        this.modifiedDate = bookPost.getModifiedDate();
    }
}
